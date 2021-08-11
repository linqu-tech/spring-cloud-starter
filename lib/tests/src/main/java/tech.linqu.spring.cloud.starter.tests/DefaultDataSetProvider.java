package tech.linqu.spring.cloud.starter.tests;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.springframework.core.annotation.AnnotationUtils;
import tech.linqu.spring.cloud.starter.tests.annotation.DataProvider;
import tech.linqu.spring.cloud.starter.tests.annotation.TeardownTables;
import tech.linqu.spring.cloud.starter.tests.provider.RecordProvider;
import tech.linqu.spring.cloud.starter.tests.provider.RecordsProvider;
import tech.linqu.spring.cloud.starter.tests.provider.TablesProvider;

/**
 * Default implementation of {@link DataSetProvider}.
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultDataSetProvider implements DataSetProvider {

    private final DSLContext dsl;

    /**
     * Setup test data.
     *
     * @param type   {@link Class}
     * @param method {@link Method}
     */
    @Override
    public void setup(Class<?> type, Method method) {
        try {
            Set<TableRecord<?>> records = getDataRecords(type, method);
            for (TableRecord<?> record : records) {
                dsl.executeInsert(record);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Teardown test data.
     *
     * @param type   {@link Class}
     * @param method {@link Method}
     */
    @Override
    public void teardown(Class<?> type, Method method) {
        try {
            for (Table<?> table : getTables(type)) {
                dsl.truncate(table).execute();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Set<TableRecord<?>> getDataRecords(Class<?> type, Method method) throws Exception {
        Set<TableRecord<?>> records = new HashSet<>();
        Set<Class<? extends RecordProvider<?>>> recordProviders = new HashSet<>();
        DataProvider methodProvider = AnnotationUtils.findAnnotation(method, DataProvider.class);
        if (methodProvider != null) {
            if (!methodProvider.ignored()) {
                recordProviders.addAll(Arrays.asList(methodProvider.value()));
                records.addAll(invokeProvider(methodProvider.provider()));
            }
        }
        if (methodProvider == null || methodProvider.merge()) {
            DataProvider typeProvider = AnnotationUtils.findAnnotation(type, DataProvider.class);
            if (typeProvider != null && !typeProvider.ignored()) {
                recordProviders.addAll(Arrays.asList(typeProvider.value()));
                records.addAll(invokeProvider(typeProvider.provider()));
            }
        }
        for (Class<? extends RecordProvider<?>> providerType : recordProviders) {
            RecordProvider<?> provider = providerType.getConstructor().newInstance();
            records.add(provider.get());
        }
        return records;
    }

    private Collection<TableRecord<?>> invokeProvider(Class<? extends RecordsProvider> type)
        throws Exception {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            return Collections.emptyList();
        }
        Constructor<? extends RecordsProvider> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        RecordsProvider provider = constructor.newInstance();
        return provider.get();
    }

    private Set<Table<?>> getTables(Class<?> type) throws Exception {
        TeardownTables annotation = AnnotationUtils.findAnnotation(type, TeardownTables.class);
        if (annotation == null) {
            return Collections.emptySet();
        }
        Class<? extends TablesProvider> providerType = annotation.value();
        TablesProvider provider = providerType.getConstructor().newInstance();
        return provider.get();
    }
}

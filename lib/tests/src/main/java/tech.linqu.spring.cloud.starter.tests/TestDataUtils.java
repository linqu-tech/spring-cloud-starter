package tech.linqu.spring.cloud.starter.tests;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import org.jooq.Table;

/**
 * Utility to handle test data.
 */
public class TestDataUtils {

    private TestDataUtils() {
    }

    /**
     * Resolve tables from JOOQ generated tables.
     *
     * @param type generated tables class
     * @return tables
     */
    public static Set<Table<?>> resolveTables(Class<?> type) {
        Set<Table<?>> tables = new HashSet<>();
        for (Field field : type.getDeclaredFields()) {
            int mod = field.getModifiers();
            if (!Modifier.isStatic(mod) || !Modifier.isPublic(mod)) {
                continue;
            }
            if (Table.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                tables.add((Table<?>) getField(field, null));
            }
        }
        return tables;
    }

    /**
     * Get field from object.
     *
     * @param field {@link Field}
     * @param obj   {@link Object}
     * @return {@link Object}
     */
    public static Object getField(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

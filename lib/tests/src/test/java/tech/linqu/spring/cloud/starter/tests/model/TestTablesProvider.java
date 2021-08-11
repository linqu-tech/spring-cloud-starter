package tech.linqu.spring.cloud.starter.tests.model;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jooq.Table;
import tech.linqu.spring.cloud.starter.tests.provider.TablesProvider;

/**
 * Class for test.
 */
public class TestTablesProvider implements TablesProvider {

    @Override
    public Set<Table<?>> get() {
        return new HashSet<>(Arrays.asList(
            (Table<?>) mock(Table.class),
            (Table<?>) mock(Table.class)
        ));
    }
}

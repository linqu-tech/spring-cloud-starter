package tech.linqu.spring.cloud.starter.account.tests;

import java.util.Collections;
import java.util.Set;
import org.jooq.Table;
import tech.linqu.spring.cloud.starter.tests.provider.TablesProvider;

/**
 * Generated tables provider.
 */
public class SchemaTablesProvider implements TablesProvider {

    private final Set<Table<?>> tables = Collections.emptySet();

    /**
     * Get set of tables.
     *
     * @return {@link Set}
     */
    @Override
    public Set<Table<?>> get() {
        return tables;
    }
}

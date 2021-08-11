package tech.linqu.spring.cloud.starter.auth.tests;

import java.util.Set;
import org.jooq.Table;
import tech.linqu.spring.cloud.starter.auth.schema.Tables;
import tech.linqu.spring.cloud.starter.tests.TestDataUtils;
import tech.linqu.spring.cloud.starter.tests.provider.TablesProvider;

/**
 * Generated tables provider.
 */
public class SchemaTablesProvider implements TablesProvider {

    private final Set<Table<?>> tables = TestDataUtils.resolveTables(Tables.class);

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

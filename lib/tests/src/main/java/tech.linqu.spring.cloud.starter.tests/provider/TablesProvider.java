package tech.linqu.spring.cloud.starter.tests.provider;

import java.util.Set;
import org.jooq.Table;

/**
 * Provide a {@link Set} of {@link Table}.
 */
public interface TablesProvider {

    /**
     * Get a {@link Set} of {@link Table}.
     *
     * @return {@link Set}
     */
    Set<Table<?>> get();
}

package tech.linqu.spring.cloud.starter.tests.provider;

import java.util.Collection;
import org.jooq.TableRecord;

/**
 * Provide a {@link Collection} of {@link TableRecord}.
 */
public interface RecordsProvider {

    /**
     * Get a {@link Collection} of {@link TableRecord}.
     *
     * @return {@link Collection}
     */
    Collection<TableRecord<?>> get();
}

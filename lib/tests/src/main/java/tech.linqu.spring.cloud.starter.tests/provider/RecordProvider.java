package tech.linqu.spring.cloud.starter.tests.provider;

import org.jooq.TableRecord;

/**
 * Provide a {@link TableRecord}.
 *
 * @param <T> {@link RecordProvider}
 */
public interface RecordProvider<T extends TableRecord<T>> {

    /**
     * Get a {@link TableRecord}.
     *
     * @return type extends {@link TableRecord}
     */
    T get();
}

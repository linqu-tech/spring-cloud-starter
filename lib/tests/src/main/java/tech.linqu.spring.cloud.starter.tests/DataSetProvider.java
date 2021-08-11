package tech.linqu.spring.cloud.starter.tests;

import java.lang.reflect.Method;

/**
 * Provider to setup and teardown test data.
 */
public interface DataSetProvider {

    /**
     * Setup test data.
     *
     * @param type   {@link Class}
     * @param method {@link Method}
     */
    void setup(Class<?> type, Method method);

    /**
     * Teardown test data.
     *
     * @param type   {@link Class}
     * @param method {@link Method}
     */
    void teardown(Class<?> type, Method method);
}

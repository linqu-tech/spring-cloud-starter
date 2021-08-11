package tech.linqu.spring.cloud.starter.tests;

import java.lang.reflect.Method;
import org.jooq.DSLContext;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Data provider extension for setup and teardown test data.
 */
public class DataProviderExtension implements BeforeAllCallback, BeforeTestExecutionCallback,
    AfterTestExecutionCallback {

    private DataSetProvider dataSetProvider;

    /**
     * Before all tests.
     *
     * @param context {@link ExtensionContext}
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        this.dataSetProvider = new DefaultDataSetProvider(
            applicationContext.getBean(DSLContext.class)
        );
    }

    /**
     * Before test execution.
     *
     * @param context {@link ExtensionContext}
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) {
        Class<?> type = context.getTestClass().orElse(null);
        Method method = context.getTestMethod().orElse(null);
        dataSetProvider.setup(type, method);
    }

    /**
     * After test execution.
     *
     * @param context {@link ExtensionContext}
     */
    @Override
    public void afterTestExecution(ExtensionContext context) {
        Class<?> type = context.getTestClass().orElse(null);
        Method method = context.getTestMethod().orElse(null);
        dataSetProvider.teardown(type, method);
    }
}

package tech.linqu.spring.cloud.starter.tests.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import tech.linqu.spring.cloud.starter.tests.provider.TablesProvider;

/**
 * Teardown tables after each test.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TeardownTables {

    /**
     * Tables to teardown.
     *
     * @return class of {@link TablesProvider}
     */
    Class<? extends TablesProvider> value() default TablesProvider.class;
}

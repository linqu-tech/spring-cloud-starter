package tech.linqu.spring.cloud.starter.tests.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import tech.linqu.spring.cloud.starter.tests.provider.RecordProvider;
import tech.linqu.spring.cloud.starter.tests.provider.RecordsProvider;

/**
 * Data provider annotation.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataProvider {

    /**
     * Provider to provide data records.
     *
     * @return class of {@link RecordProvider}
     */
    Class<? extends RecordProvider<?>>[] value() default {};

    /**
     * Provider to provide data records.
     *
     * @return class of {@link RecordsProvider}
     */
    Class<? extends RecordsProvider> provider() default RecordsProvider.class;

    /**
     * Whether ignore this provider.
     *
     * @return true if ignore
     */
    boolean ignored() default false;

    /**
     * Whether merge this provider with others.
     *
     * @return true if merge
     */
    boolean merge() default false;
}

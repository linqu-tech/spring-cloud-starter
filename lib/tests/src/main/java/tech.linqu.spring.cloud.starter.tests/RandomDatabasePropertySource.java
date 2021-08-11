package tech.linqu.spring.cloud.starter.tests;

import java.util.Objects;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Property source for random database name.
 */
public class RandomDatabasePropertySource extends RandomValuePropertySource {

    private static final RandomValuePropertySource source = new RandomDatabasePropertySource();

    private static final String PREFIX = "random.";

    private final String database = Objects.toString(getProperty("random.uuid"));

    /**
     * Get property.
     *
     * @param name property name
     * @return value {@link Object}
     */
    @Override
    public Object getProperty(String name) {
        if (!name.startsWith(PREFIX)) {
            return null;
        }
        String type = name.substring(PREFIX.length());
        if ("database".equals(type)) {
            return database;
        }
        return super.getProperty(name);
    }

    /**
     * Add source to environment.
     *
     * @param environment {@link ConfigurableEnvironment}
     */
    public static void addToEnvironment(ConfigurableEnvironment environment) {
        environment
            .getPropertySources()
            .replace(RandomValuePropertySource.RANDOM_PROPERTY_SOURCE_NAME, source);
    }
}

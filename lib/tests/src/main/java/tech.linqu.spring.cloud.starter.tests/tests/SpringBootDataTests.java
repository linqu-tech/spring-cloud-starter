package tech.linqu.spring.cloud.starter.tests.tests;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import tech.linqu.spring.cloud.starter.tests.DataProviderExtension;

/**
 * Tests with data provider.
 */
@ActiveProfiles("test")
@ContextConfiguration
@ExtendWith(DataProviderExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SpringBootDataTests {

}

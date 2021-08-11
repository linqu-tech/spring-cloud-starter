package tech.linqu.spring.cloud.starter.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(ConfigApplication::new);
        try (MockedStatic<SpringApplication> application = mockStatic(SpringApplication.class)) {
            assertNotNull(application);
            ConfigApplication.main(null);
        }
    }
}

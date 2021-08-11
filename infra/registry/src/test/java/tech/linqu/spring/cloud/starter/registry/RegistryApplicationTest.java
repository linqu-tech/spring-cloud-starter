package tech.linqu.spring.cloud.starter.registry;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegistryApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(RegistryApplication::new);
        try (MockedStatic<SpringApplication> application = mockStatic(SpringApplication.class)) {
            assertNotNull(application);
            RegistryApplication.main(null);
        }
    }
}

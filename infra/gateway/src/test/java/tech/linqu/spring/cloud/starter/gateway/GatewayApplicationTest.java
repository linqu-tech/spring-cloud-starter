package tech.linqu.spring.cloud.starter.gateway;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GatewayApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(GatewayApplication::new);
        try (MockedStatic<SpringApplication> application = mockStatic(SpringApplication.class)) {
            assertNotNull(application);
            GatewayApplication.main(null);
        }
    }
}

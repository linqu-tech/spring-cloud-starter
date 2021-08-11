package tech.linqu.spring.cloud.starter.account;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.linqu.spring.cloud.starter.account.tests.SpringBootTestConfiguration;

@ExtendWith(SpringExtension.class)
@Import(SpringBootTestConfiguration.class)
@SpringBootTest
class AccountApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(AccountApplication::new);
        try (MockedStatic<SpringApplication> application = mockStatic(SpringApplication.class)) {
            assertNotNull(application);
            AccountApplication.main(null);
        }
    }
}

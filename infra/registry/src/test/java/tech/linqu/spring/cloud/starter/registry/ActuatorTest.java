package tech.linqu.spring.cloud.starter.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ActuatorTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    void whenGetActuatorHealth_shouldSucceedWith200() {
        ResponseEntity<String> result = template
            .getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

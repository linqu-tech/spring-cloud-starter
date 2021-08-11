package tech.linqu.spring.cloud.starter.tests.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class SpringComponentTestsTest {

    @Test
    void shouldConstructSuccess() {
        assertDoesNotThrow(() -> new SpringComponentTests() {
        });
    }
}

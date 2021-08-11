package tech.linqu.spring.cloud.starter.tests.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class SpringBootMvcTestsTest {

    private static class TestSpringBootMvcTests extends SpringBootMvcTests {

        public TestSpringBootMvcTests(MockMvc mvc) {
            this.mvc = mvc;
        }
    }

    @Test
    void shouldPerformRequestSuccess() throws Exception {
        MockMvc mvc = mock(MockMvc.class);
        ResultActions mockActions = mock(ResultActions.class);
        when(mvc.perform(any())).thenReturn(mockActions);
        SpringBootMvcTests tests = new TestSpringBootMvcTests(mvc);
        ResultActions actions = tests.perform(MockMvcRequestBuilders.get("https://url.com"));
        assertNotNull(actions);
    }

    @Test
    void shouldPerformRequestThrowException() {
        SpringBootMvcTests tests = new TestSpringBootMvcTests(null);
        assertThrows(RuntimeException.class,
            () -> tests.perform(MockMvcRequestBuilders.get("https://url.com")));
    }
}

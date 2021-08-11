package tech.linqu.spring.cloud.starter.tests.tests;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tech.linqu.spring.cloud.starter.tests.DataProviderExtension;
import tech.linqu.spring.cloud.starter.tests.TestMvcUtils;
import tech.linqu.webpb.runtime.WebpbMessage;

/**
 * Tests for spring mvc.
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
@ExtendWith(DataProviderExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SpringBootMvcTests {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * Mocked MVC.
     */
    @Autowired
    protected MockMvc mvc;

    /**
     * Create a request builder.
     *
     * @param message     {@link WebpbMessage}
     * @return {@link MockHttpServletRequestBuilder}
     */
    protected MockHttpServletRequestBuilder request(WebpbMessage message) {
        return TestMvcUtils.request(message, contextPath);
    }

    /**
     * Perform a request.
     *
     * @param requestBuilder {@link RequestBuilder}
     * @return {@link ResultActions}
     */
    protected ResultActions perform(RequestBuilder requestBuilder) {
        return TestMvcUtils.perform(mvc, requestBuilder);
    }
}

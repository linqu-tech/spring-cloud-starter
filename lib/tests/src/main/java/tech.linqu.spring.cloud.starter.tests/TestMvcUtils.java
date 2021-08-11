package tech.linqu.spring.cloud.starter.tests;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.linqu.webpb.runtime.WebpbMessage;
import tech.linqu.webpb.runtime.WebpbUtils;

/**
 * Test utility for spring mvc.
 */
public class TestMvcUtils {

    private TestMvcUtils() {
    }

    /**
     * Create a request builder.
     *
     * @param message     {@link WebpbMessage}
     * @param contextPath context path
     * @return {@link MockHttpServletRequestBuilder}
     */
    public static MockHttpServletRequestBuilder request(WebpbMessage message, String contextPath) {
        HttpMethod method = HttpMethod.valueOf(message.webpbMeta().getMethod());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
            .request(method, WebpbUtils.formatUrl(message))
            .contextPath(contextPath)
            .contentType(MediaType.APPLICATION_JSON);
        if (method != HttpMethod.GET) {
            builder.content(WebpbUtils.serialize(message));
        }
        return builder;
    }

    /**
     * Perform a request.
     *
     * @param mvc            {@link MockMvc}
     * @param requestBuilder {@link RequestBuilder}
     * @return {@link ResultActions}
     */
    public static ResultActions perform(MockMvc mvc, RequestBuilder requestBuilder) {
        try {
            return mvc.perform(requestBuilder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

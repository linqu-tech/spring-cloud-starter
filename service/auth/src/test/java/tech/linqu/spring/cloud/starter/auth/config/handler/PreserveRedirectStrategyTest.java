package tech.linqu.spring.cloud.starter.auth.config.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

class PreserveRedirectStrategyTest {

    private void testWithRedirect(String expect, String url) throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getParameter("redirect")).thenReturn("https://a.com");
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.encodeRedirectURL(any())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });
        PreserveRedirectStrategy strategy = new PreserveRedirectStrategy();
        strategy.sendRedirect(request, response, url);
        verify(response).sendRedirect(expect);
    }

    @Test
    void givenWithRedirect_whenSendRedirect_shouldSuccess() throws IOException {
        testWithRedirect("https//origin.com?redirect=https://a.com", "https//origin.com");
    }

    @Test
    void givenWithQuery_whenSendRedirect_shouldSuccess() throws IOException {
        testWithRedirect("https//origin.com?q=1&redirect=https://a.com", "https//origin.com?q=1");
    }

    @Test
    void givenWithEmptyQuery_whenSendRedirect_shouldSuccess() throws IOException {
        testWithRedirect("https//origin.com?redirect=https://a.com", "https//origin.com?");
    }

    @Test
    void givenNoRedirect_shouldSendRedirectSuccess() throws IOException {
        PreserveRedirectStrategy strategy = new PreserveRedirectStrategy();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.encodeRedirectURL(any())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });
        strategy.sendRedirect(request, response, "https//origin.com");
        verify(response).sendRedirect("https//origin.com");
    }
}

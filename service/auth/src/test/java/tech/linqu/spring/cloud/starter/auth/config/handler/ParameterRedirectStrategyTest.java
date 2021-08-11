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

class ParameterRedirectStrategyTest {

    @Test
    void givenWithRedirect_whenSendRedirect_shouldSuccess() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        when(request.getParameter("redirect")).thenReturn("https://a.com");
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.encodeRedirectURL(any())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });
        ParameterRedirectStrategy strategy = new ParameterRedirectStrategy();
        strategy.sendRedirect(request, response, "any");
        verify(response).sendRedirect("https://a.com");
    }

    @Test
    void givenWithoutRedirect_whenSendRedirect_ShouldSuccess() throws IOException {
        ParameterRedirectStrategy strategy = new ParameterRedirectStrategy();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("");
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.encodeRedirectURL(any())).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            return (String) args[0];
        });
        strategy.sendRedirect(request, response, "https://a.com");
        verify(response).sendRedirect("https://a.com");
    }
}

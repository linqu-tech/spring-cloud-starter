package tech.linqu.spring.cloud.starter.utilities.mvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

class PageableMethodArgumentResolverTest {

    @Test
    void shouldResolveArgumentSuccess() throws Exception {
        Method method = TestClass.class.getDeclaredMethod("test", Pageable.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        ModelAndViewContainer container = mock(ModelAndViewContainer.class);
        NativeWebRequest request = mockRequest();
        WebDataBinderFactory factory = mock(WebDataBinderFactory.class);
        PageableMethodArgumentResolver resolver = new PageableMethodArgumentResolver(false);
        Pageable pageable = resolver.resolveArgument(parameter, container, request, factory);
        assertTrue(pageable.isPaged());
    }

    @Test
    void shouldResolveArgumentSuccessWhenForcePageable() throws Exception {
        Method method = TestClass.class.getDeclaredMethod("test", Pageable.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        ModelAndViewContainer container = mock(ModelAndViewContainer.class);
        NativeWebRequest request = mockRequest();
        WebDataBinderFactory factory = mock(WebDataBinderFactory.class);
        PageableMethodArgumentResolver resolver = new PageableMethodArgumentResolver(true);
        Pageable pageable = resolver.resolveArgument(parameter, container, request, factory);
        assertTrue(pageable.isPaged());
    }

    @Test
    void shouldResolveArgumentSuccessWhenPaginationIsFalse() throws Exception {
        Method method = TestClass.class.getDeclaredMethod("test", Pageable.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        ModelAndViewContainer container = mock(ModelAndViewContainer.class);
        NativeWebRequest request = mockRequest();
        when(request.getParameter("pagination")).thenReturn("false");
        WebDataBinderFactory factory = mock(WebDataBinderFactory.class);
        PageableMethodArgumentResolver resolver = new PageableMethodArgumentResolver(false);
        Pageable pageable = resolver.resolveArgument(parameter, container, request, factory);
        assertTrue(pageable.isUnpaged());
    }

    private NativeWebRequest mockRequest() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        NativeWebRequest nativeRequest = mock(NativeWebRequest.class);
        doReturn(servletRequest).when(nativeRequest).getNativeRequest(HttpServletRequest.class);
        return nativeRequest;
    }
}

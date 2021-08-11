package tech.linqu.spring.cloud.starter.account.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.external.error.ErrorCode;
import tech.linqu.spring.cloud.starter.proto.imports.error.ErrorMessage;
import tech.linqu.spring.cloud.starter.proto.imports.error.ValidationErrorMessage;

class GlobalExceptionHandlerTest {

    void testMethod(Integer a) {
    }

    @Test
    void shouldHandleValidationExceptionSuccess() throws NoSuchMethodException {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Method method =
            GlobalExceptionHandlerTest.class.getDeclaredMethod("testMethod", Integer.class);
        MethodParameter parameter = new MethodParameter(method, 0);
        BindingResult result = new BeanPropertyBindingResult(handler, "handler");
        result.addError(new FieldError("object", "field", "Error"));
        MethodArgumentNotValidException exception =
            new MethodArgumentNotValidException(parameter, result);
        ValidationErrorMessage message = handler.handleValidationException(exception);
        assertEquals(1, message.getErrors().size());
    }

    @Test
    void shouldHandleApiExceptionSuccess() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<?> entity = handler.handleApiException(ApiException.of(ErrorCode.ERROR));
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    void shouldAccessDeniedExceptionSuccess() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        AccessDeniedException exception = new AccessDeniedException("Error");
        ErrorMessage message = handler.handleAccessDeniedException(exception);
        assertEquals("Error", message.getMessage());
    }

    @Test
    void shouldGenericExceptionSuccess() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        IllegalAccessException exception = new IllegalAccessException("Error");
        ResponseEntity<?> entity = handler.handleGenericException(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
    }
}

package tech.linqu.spring.cloud.starter.auth.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.linqu.spring.cloud.starter.proto.exception.ApiException;
import tech.linqu.spring.cloud.starter.proto.external.error.ErrorCode;
import tech.linqu.spring.cloud.starter.proto.imports.error.ErrorMessage;
import tech.linqu.spring.cloud.starter.proto.imports.error.ValidationErrorMessage;

/**
 * Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Format validation exception error message.
     *
     * @param ex {@link MethodArgumentNotValidException}
     * @return map of messages
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorMessage handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ValidationErrorMessage(errors);
    }

    /**
     * Handle api exception.
     *
     * @param ex {@link ApiException}
     * @return {@link ErrorMessage}
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        return ResponseEntity
            .status(ex.getStatus())
            .body(new ErrorMessage()
                .setCode(ex.getErrorValue())
                .setMessage(ex.getMessage())
            );
    }

    /**
     * Handle access denied exception.
     *
     * @param ex {@link AccessDeniedException}
     * @return {@link ErrorMessage}
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorMessage handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorMessage(ErrorCode.ERROR.getValue(), ex.getMessage());
    }

    /**
     * Handle generic exception.
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorMessage(ErrorCode.ERROR.getValue(), ex.getMessage()));
    }
}

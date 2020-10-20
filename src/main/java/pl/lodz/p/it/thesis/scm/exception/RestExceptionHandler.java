package pl.lodz.p.it.thesis.scm.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.lodz.p.it.thesis.scm.util.RestMessage;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final String UNEXPECTED_ERROR = "Exception.unexpected";
    private static final String USER_DISABLED = "Exception.user.disabled";
    private static final String BAD_CREDENTIALS = "Exception.bad.credentials";
    private static final String OPTIMISTIC_LOCK = "Exception.different.version";

    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestMessage> handleIllegalArgument(RestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestMessage> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> messageSource.getMessage(objectError, locale))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new RestMessage(errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestMessage> handleExceptions(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
        ex.printStackTrace();
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<RestMessage> handleDisabledException(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(USER_DISABLED, null, locale);
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestMessage> handleBadCredentialsException(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(BAD_CREDENTIALS, null, locale);
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<RestMessage> handleOptimisticLockingFailureException(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(OPTIMISTIC_LOCK, null, locale);
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(IfMatchValueException.class)
    public ResponseEntity<RestMessage> handleIfMatchValueException(Exception ex, Locale locale) {
        String errorMessage = messageSource.getMessage(OPTIMISTIC_LOCK, null, locale);
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.PRECONDITION_FAILED);
    }


}

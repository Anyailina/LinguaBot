package org.annill.linguabot.configuration;

import org.annill.linguabot.configuration.errors.ExceptionDetails;
import org.annill.linguabot.enums.ExceptionEnum;
import org.annill.linguabot.exception.InternalException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

public class ControllerAdviceConfiguration {
    private final ExceptionDetails exceptionDetails;

    public ControllerAdviceConfiguration(ExceptionDetails exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    @ResponseBody
    @ExceptionHandler({InternalException.class})
    public ResponseEntity<?> handleEntityNotFoundException(InternalException exception) {
        var mapError = exceptionDetails.getErrorDetails();
        var exceptionProperties = Optional.ofNullable(mapError.get(exception.getException()))
                .orElseGet(() -> mapError.get(ExceptionEnum.DEFAULT));

        return new ResponseEntity<>(exceptionProperties, HttpStatusCode.valueOf(exceptionProperties.getHttpCode()));
    }

    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException() {
        var mapError = exceptionDetails.getErrorDetails();
        var exceptionProperties = mapError.get(ExceptionEnum.DEFAULT);

        return new ResponseEntity<>(exceptionProperties, HttpStatusCode.valueOf(exceptionProperties.getHttpCode()));
    }
}

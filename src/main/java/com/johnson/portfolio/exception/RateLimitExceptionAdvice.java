package com.johnson.portfolio.exception;

import com.johnson.portfolio.Models.ContactResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RateLimitExceptionAdvice {

    // Handle CustomException
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitException(RateLimitExceededException ex) {
        return new ResponseEntity<>(new ContactResponse(HttpStatus.TOO_MANY_REQUESTS.value(),
                HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(), "Too many requests.", null),
                HttpStatus.TOO_MANY_REQUESTS);
    }

}

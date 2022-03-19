package jh.hw.utils;

import jh.hw.model.JsonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<JsonResponse> exception(Exception exception) {
        return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
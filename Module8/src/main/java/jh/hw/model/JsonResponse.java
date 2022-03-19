package jh.hw.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonResponse {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private String status;
    private String message;
    private String time;

    public static ResponseEntity<JsonResponse> buildResponse(HttpStatus httpStatus) {
        return buildResponse(httpStatus, httpStatus.getReasonPhrase());
    }

    public static ResponseEntity<JsonResponse> buildResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(new JsonResponse(httpStatus, message));
    }

    public JsonResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, message, ZonedDateTime.now().format(DATE_TIME_FORMATTER));
    }

    public JsonResponse(HttpStatus httpStatus, String message, String time) {
        this.status = httpStatus.toString();
        this.message = message;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
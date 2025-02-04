package org.bankapp.bankingapp.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> response(
            String message,
            HttpStatus httpStatus,
            Object data
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", httpStatus.value());
        responseBody.put("message", message);
        responseBody.put("data", data);
        return new ResponseEntity<>(responseBody, httpStatus);
    }
}

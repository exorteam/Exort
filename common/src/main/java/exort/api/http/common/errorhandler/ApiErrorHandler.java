package exort.api.http.common.errorhandler;

import exort.api.http.common.entity.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler({ApiError.class})
    public ResponseEntity<ApiResponse> handleError(ApiError e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(e.getError(), e.getMessage()), e.getStatus());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse> handleJsonError(HttpMessageNotReadableException e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse("bad_json", e.getMessage()), HttpStatus.valueOf(400));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> handleOtherError(Exception e, WebRequest request) {
        e.printStackTrace();
        return new ResponseEntity<>(new ApiResponse("internal_server_error", e.getMessage()), HttpStatus.valueOf(500));
    }
}

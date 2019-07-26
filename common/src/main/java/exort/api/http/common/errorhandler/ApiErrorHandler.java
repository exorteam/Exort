package exort.api.http.common.errorhandler;

import exort.api.http.common.entity.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiErrorHandler {

    @ExceptionHandler({ApiError.class})
    public ResponseEntity<ApiResponse> handleApiError(ApiError e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(e.getError(), e.getMessage()), e.getStatus());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse> handleBodyParameterError(HttpMessageNotReadableException e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse("bad_body_parameter", e.getMessage()), HttpStatus.valueOf(400));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse> handlePathParameterError(MethodArgumentTypeMismatchException e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse("bad_path_parameter", e.getMessage()), HttpStatus.valueOf(400));
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ApiResponse> handleQueryParameterError(BindException e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse("bad_query_parameter", e.getMessage()), HttpStatus.valueOf(400));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResponse> handleMethodError(HttpRequestMethodNotSupportedException e, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse("bad_method", e.getMessage()), HttpStatus.valueOf(405));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> handleOtherError(Exception e, WebRequest request) {
        e.printStackTrace();
        return new ResponseEntity<>(new ApiResponse("internal_server_error", e.getMessage()), HttpStatus.valueOf(500));
    }

}

package exort.api.http.common.errorhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError extends RuntimeException {

    private HttpStatus status;
    private String error;
    private String message;

    public ApiError(int code, String error, String message) {
        this.status = HttpStatus.valueOf(code);
        this.error = error == null ? "" : error;
        this.message = message == null ? "" : message;
    }

}

package exort.api.http.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private T data = null;
    private String error = "";
    private String message = "";

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public static ApiResponse emptyResponse() {
        return new ApiResponse<>(new HashMap());
    }
}

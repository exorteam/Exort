package exort.api.http.entity;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data = null;
    private String error = "";
    private String message = "";
}

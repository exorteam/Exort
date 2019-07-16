package exort.association_member_management.dto;

import lombok.Data;

@Data
public class ResponseCode<T> {
    private T data = null;
    private String error = "";
    private String message = "";
}

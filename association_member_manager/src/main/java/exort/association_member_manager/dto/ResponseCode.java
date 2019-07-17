package exort.association_member_manager.dto;

import lombok.Data;

@Data
public class ResponseCode<T> {
    private T data = null;
    private String error = "";
    private String message = "";
}

package exort.associationmanager.entity;


import lombok.Data;

@Data
public class ResponseBody<T> {
    private T data;
    private String error;
    private String message;
}

package exort.associationmanager.entity;

import java.time.format.DateTimeFormatter;

import java.util.Date;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ResponseBody {
    private T data;
    private String error;
    private String message;
}

package exort.api.http.entity;

import lombok.Data;

import java.util.List;

@Data
public class OperationBatch<ArgType> {
    private String operation;
    private List<ArgType> args;
}

package exort.api.http.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationBatch<ArgType> {
    private String operation;
    private List<ArgType> args;
}

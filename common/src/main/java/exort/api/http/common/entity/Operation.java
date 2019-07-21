package exort.api.http.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation<ArgType> {
    private String operation;
    private ArgType arg;
}

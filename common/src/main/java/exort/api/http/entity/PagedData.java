package exort.api.http.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedData<DataType> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalSize;
    private List<DataType> content;
}

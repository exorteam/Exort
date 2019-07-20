package exort.api.http.entity;

import lombok.Data;

import java.util.List;

@Data
public class PagedData<DataType> {
    private int pageNum;
    private int pageSize;
    private int totalSize;
    private List<DataType> content;
}

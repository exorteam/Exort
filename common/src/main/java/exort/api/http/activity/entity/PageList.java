package exort.api.http.activity.entity;

import java.util.List;

import lombok.Data;

@Data
public class PageList<T> {

    private int pageSize;
    private int pageNum;
    private int totalSize;
    private List<T> content;

    public PageList(int pagesize, int pagenum, int totalsize, List<T> content) {
        this.pageSize = pagesize;
        this.pageNum = pagenum;
        this.totalSize = totalsize;
        this.content = content;
    }
}
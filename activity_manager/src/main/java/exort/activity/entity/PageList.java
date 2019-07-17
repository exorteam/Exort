package exort.activity.entity;

import java.util.List;

public class PageList<T> {

    private int pageSize;
    private int pageNum;
    private int totalSize;
    private List<T> content;

    public PageList(int pagesize, int pagenum, int totalsize, List<T> content){
            this.pageSize = pagesize;
            this.pageNum = pagenum;
            this.totalSize = totalsize;
            this.content = content;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

}


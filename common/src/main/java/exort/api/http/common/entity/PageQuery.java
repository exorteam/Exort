package exort.api.http.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery {
    private Integer pageNum;
    private Integer pageSize;
    private String sortBy;

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static PageQuery relocate(PageQuery pq, Integer defaultSize, Integer maxSize) {
        int num = pq.getPageNum() == null ? 0 : pq.getPageNum();
        int size = pq.getPageSize() == null ? defaultSize : pq.getPageSize();
        long start = num * (long)size;
        if (size > maxSize) {
            size = maxSize;
            num = start / size > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)(start / size);
        }
        return new PageQuery(num, size, pq.getSortBy());
    }
}

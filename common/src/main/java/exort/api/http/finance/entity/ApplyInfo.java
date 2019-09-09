package exort.api.http.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyInfo {
    // 项目名称
    private String projectName;
    // 社团ID
    private String associationId;
    // 社团名称
    private String associationName;
    // 金额使用情况说明
    private String content;
    // 负责人
    private String supervisor;
    // 使用金额
    private Float transactionAmount;
    // 往来方向 "汇入"/"汇出"
    private String direction;
    // 操作者ID
    private Integer operatorId;
}

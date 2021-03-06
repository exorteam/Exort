package exort.api.http.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinanceInfo implements Serializable {
    // 项目号
    private String id;
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
    // 录入时间
    private Date time;
    // 使用金额
    private Float transactionAmount;
    // 往来方向 "汇入"/"汇出"
    private String direction;
    // 操作者ID
    private Integer operatorId;
    // 余额
    private Float balance;
    // 申请状态 (1：申请中；2：申请通过；3：申请拒绝)
    private Integer state;


    public FinanceInfo(String projectName, String associationId, String associationName, String content, String supervisor, Float transactionAmount, String direction, Integer operatorId) {
        this.projectName = projectName;
        this.associationId = associationId;
        this.associationName = associationName;
        this.content = content;
        this.supervisor = supervisor;
        this.time = new Date();
        this.transactionAmount = transactionAmount;
        this.direction = direction;
        this.operatorId = operatorId;
        this.state = 1;
    }
}

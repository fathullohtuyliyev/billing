package uz.ichange.billing.billing.payme.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class GetStatementResult implements Serializable {
    private String id;
    private Long time;
    private Double amount;
    private Account account;
    private Long createTime;
    private Long performTime;
    private Long cancelTime;
    private Long transaction;
    private Integer state;
    private Integer reason;
}

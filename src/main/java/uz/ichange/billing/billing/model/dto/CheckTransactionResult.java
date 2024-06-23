package uz.ichange.billing.billing.model.dto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class CheckTransactionResult implements Serializable {
    private Long createTime;
    private Long performTime;
    private Long cancelTime;
    private String transaction;
    private Integer state;
    private Integer reason;
}
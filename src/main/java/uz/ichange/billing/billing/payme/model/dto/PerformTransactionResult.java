package uz.ichange.billing.billing.payme.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PerformTransactionResult implements Serializable {
    private String transaction;
    private Long performTime;
    private Integer state;
}

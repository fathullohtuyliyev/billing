package uz.ichange.billing.billing.model.dto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CreateTransactionResult implements Serializable {
    private Long createTime;
    private String transaction;
    private Integer state;
}

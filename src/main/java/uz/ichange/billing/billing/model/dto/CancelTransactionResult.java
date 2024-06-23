package uz.ichange.billing.billing.model.dto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CancelTransactionResult implements Serializable {
    private String transaction;
    private Long cancelTime;
    private Integer state;
}

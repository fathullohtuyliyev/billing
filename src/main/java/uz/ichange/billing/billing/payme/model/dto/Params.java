package uz.ichange.billing.billing.payme.model.dto;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Params implements Serializable {
    private String id;
    private Long time;
    private Long from;
    private Long to;
    private Double amount;
    private Account account;
    private OrderCancelReason reason;
    private String password;
}

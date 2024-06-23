package uz.ichange.billing.billing.payme.model.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Account implements Serializable {
    private String orderId;
}
package uz.ichange.billing.billing.model.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PayReq implements Serializable {
    private GrantType method;
    private Params params;
    private Long id;
}

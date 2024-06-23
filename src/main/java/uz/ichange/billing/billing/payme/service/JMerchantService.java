package uz.ichange.billing.billing.payme.service;

import uz.ichange.billing.billing.payme.model.dto.PayReq;
import uz.ichange.billing.billing.payme.model.dto.ResponsePay;

public interface JMerchantService {
    ResponsePay CheckPerformTransaction(PayReq payReq);

    ResponsePay CreateTransaction(PayReq payReq);

    ResponsePay PerformTransaction(PayReq payReq);

    ResponsePay CancelTransaction(PayReq payReq);

    ResponsePay CheckTransaction(PayReq payReq);

    ResponsePay GetStatement(PayReq payReq);

    ResponsePay ChangePassword(PayReq payReq);
}

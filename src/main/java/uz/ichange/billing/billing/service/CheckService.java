package uz.ichange.billing.billing.service;

import uz.ichange.billing.billing.exception.ApiException;
import uz.ichange.billing.billing.model.dto.PayReq;

public interface CheckService {
    boolean validateCheckPerformTransactionParams(PayReq payReq) throws ApiException;

    boolean validateCreateTransactionParams(PayReq payReq) throws ApiException;

    boolean validatePerformTransactionParams(PayReq payReq) throws ApiException;

    boolean validateCancelTransactionParams(PayReq payReq) throws ApiException;

    boolean validateGetStatementParams(PayReq payReq) throws ApiException;
}

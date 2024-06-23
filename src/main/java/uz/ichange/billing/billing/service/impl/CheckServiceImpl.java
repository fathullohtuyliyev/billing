package uz.ichange.billing.billing.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ichange.billing.billing.exception.*;
import uz.ichange.billing.billing.model.dto.PayReq;
import uz.ichange.billing.billing.service.CheckService;

@RequiredArgsConstructor
@Service
public class CheckServiceImpl implements CheckService {
    @Override
    public boolean validateCheckPerformTransactionParams(PayReq params) throws ApiException {
        if (params.getParams() == null) {
            throw new ParamsException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getAmount() == null) {
            throw new AmountException(ExceptionType.IS_REQUIRED);
        } else if (params.getParams().getAccount() == null) {
            throw new AccountNotFoundException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getAccount().getOrderId() == null) {
            throw new OrderIdException(ExceptionType.IS_REQUIRED);
        }
        return true;
    }

    @Override
    public boolean validateCreateTransactionParams(PayReq params) throws ApiException {
        if (params.getParams() == null) {
            throw new ParamsException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getAmount() == null) {
            throw new AmountException(ExceptionType.IS_REQUIRED);
        } else if (params.getParams().getAccount() == null) {
            throw new AccountNotFoundException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getAccount().getOrderId() == null) {
            throw new OrderIdException(ExceptionType.IS_REQUIRED);
        } else if (params.getParams().getId() == null) {
            throw new PaycomIdException(ExceptionType.IS_REQUIRED);
        } else if (params.getParams().getTime() == null) {
            throw new TimeException(ExceptionType.IS_REQUIRED);
        }
        return true;
    }

    @Override
    public boolean validatePerformTransactionParams(PayReq params) throws ApiException {
        if (params.getParams() == null) {
            throw new ParamsException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getId() == null) {
            throw new PaycomIdException(ExceptionType.IS_REQUIRED);
        }
        return true;
    }

    @Override
    public boolean validateCancelTransactionParams(PayReq params) throws ApiException {
        if (params.getParams() == null) {
            throw new ParamsException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getReason() == null) {
            throw new CancelReasonException(ExceptionType.IS_REQUIRED);
        }
        return true;
    }

    @Override
    public boolean validateGetStatementParams(PayReq params) throws ApiException {
        if (params.getParams() == null) {
            throw new ParamsException(ExceptionType.NOT_FOUND);
        } else if (params.getParams().getFrom() == null) {
            throw new FromTimeException(ExceptionType.IS_REQUIRED);
        } else if (params.getParams().getTime() == null) {
            throw new ToTimeException(ExceptionType.IS_REQUIRED);
        }
        return true;
    }
}

package uz.ichange.billing.billing.payme.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ichange.billing.billing.payme.model.dto.*;
import uz.ichange.billing.billing.payme.model.entity.CustomerOrder;
import uz.ichange.billing.billing.payme.model.entity.OrderTransaction;
import uz.ichange.billing.billing.payme.service.JMerchantService;
import uz.ichange.billing.billing.payme.repository.OrderRepository;
import uz.ichange.billing.billing.payme.repository.TransactionRepository;
import uz.ichange.billing.repository.MultiLanguageMessageRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JMerchantServiceImpl implements JMerchantService {
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final MultiLanguageMessageRepository multiLanguageMessageRepository;
    private final ObjectMapper objectMapper;


    /**
     * billing for paycom
     */

    /**
     * http://paycom.uz/api/#merchant-api-metody-checkperformtransaction-proverka-vozmozhnosti-sozdaniya-finansovoj-tranzakcii
     */
    @Override
    public ResponsePay CheckPerformTransaction(PayReq payReq) {
        var order = orderRepository.findOne(payReq.getParams().getAccount().getOrderId());
        ResponsePay responsePay = new ResponsePay();
        Object result;
        Status error;
        if (payReq.getParams() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getAmount() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getAccount() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getAccount().getOrderId() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else {
            if (order.isEmpty()) {
                error = new Status(-31098, multiLanguageMessageRepository.findByCode(-31098));
                responsePay.setError(error);
            } else if (!payReq.getParams().getAmount().equals(order.get().getAmount())) {
                error = new Status(-31001, multiLanguageMessageRepository.findByCode(-31001));
                responsePay.setError(error);
            } else {
                result = new CheckPerformTransactionResult(true);
                responsePay.setResult(result);
            }

        }
        return responsePay;
    }

    /**
     * http://paycom.uz/api/#merchant-api-metody-createtransaction-sozdanie-finansovoj-tranzakcii
     */
    @Override
    public ResponsePay CreateTransaction(PayReq payReq) {
        ResponsePay responsePay = new ResponsePay();
        Object result;
        Status error;
        /**
         * find from Customer order by payComId
         */
        if (payReq.getParams() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getAmount() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getAccount() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getAccount().getOrderId() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getId() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getTime() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else {
            Optional<CustomerOrder> order = orderRepository.findOne(payReq.getParams().getAccount().getOrderId());
            if (order.isEmpty()) {
                error = new Status(-31050, multiLanguageMessageRepository.findByCode(-31050));
                responsePay.setError(error);
            } else {
                var orderTransaction = transactionRepository.findByOrder(order.get());
                if (orderTransaction.isEmpty()) {
                    if (checkPerformTransaction(payReq)) {
                        var newTransaction = OrderTransaction.builder()
                                .paycomId(payReq.getParams().getId())
                                .paycomTime(payReq.getParams().getTime())
                                .createTime(System.currentTimeMillis())
                                .state(TransactionState.STATE_IN_PROGRESS)
                                .order(order.get())
                                .build();
                        transactionRepository.save(newTransaction);
                        /**
                         * customerOrder save for history start
                         */
                        var customerOrder = CustomerOrder.builder()
                                .paycomId(payReq.getParams().getId())
                                .paycomTime(payReq.getParams().getTime())
                                .createTime(System.currentTimeMillis())
                                .state(TransactionState.STATE_IN_PROGRESS)
                                .build();
                        orderRepository.save(customerOrder);

                        /**
                         * customerOrder save for history finish
                         */

                        result = CreateTransactionResult.builder()
                                .createTime(newTransaction.createTime)
                                .transaction(newTransaction.id.toString())
                                .state(newTransaction.state != null ? newTransaction.state.getCode() : 0)
                                .build();
                        responsePay.setResult(result);
                    } else if (!payReq.getParams().getAmount().equals(order.get().getAmount())) {
                        error = new Status(-31001, multiLanguageMessageRepository.findByCode(-31001));
                        responsePay.setError(error);
                    }
                } else {
                    OrderTransaction transaction = orderTransaction.get();
                    if (transaction.state == TransactionState.STATE_IN_PROGRESS) {
                        if (orderTransaction.get().getPaycomId().equals(payReq.getParams().getId())) {
                            result = CreateTransactionResult.builder()
                                    .createTime(transaction.createTime)
                                    .transaction(transaction.id.toString())
                                    .state(transaction.state != null ? transaction.state.getCode() : 0)
                                    .build();
                            responsePay.setResult(result);

                        } else {
                            error = new Status(-31050, multiLanguageMessageRepository.findByCode(-31050));
                            responsePay.setError(error);
                        }
                    } else {
                        error = new Status(-31001, multiLanguageMessageRepository.findByCode(-31001));
                        responsePay.setError(error);
                    }

                }
            }
            return responsePay;
        }

    }

    /**
     * http://paycom.uz/api/#merchant-api-metody-performtransaction-provedenie-finansovoj-tranzakcii
     */
    @Override
    public ResponsePay PerformTransaction(PayReq payReq) {
        ResponsePay responsePay = new ResponsePay();
        Object result;
        Status error;
        OrderTransaction transaction = transactionRepository.findByPaycomId(payReq.getParams().getId());
        if (payReq.getParams() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getId() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else {
            if (transaction != null) {
                if (transaction.state == TransactionState.STATE_IN_PROGRESS) {
                    transaction.state = TransactionState.STATE_DONE;
                    transaction.performTime = new Date().getTime();
                    transactionRepository.save(transaction);
                    /**
                     * customerOrder save for history start
                     */
                    CustomerOrder order = transaction.getOrder();
                    order.setPerformTime(new Date().getTime());
                    order.setState(TransactionState.STATE_DONE);
                    orderRepository.save(order);


                    //todo do wallet incriment for this
                    /**
                     *
                     * driver wallet settings start
                     */

                }
            } else if (transaction.state == TransactionState.STATE_DONE) {
                result = PerformTransactionResult.builder()
                        .transaction(transaction.id.toString())
                        .performTime(transaction.performTime)
                        .state(transaction.state != null ? transaction.state.getCode() : 0)
                        .build();
                responsePay.setResult(result);
            } else {
                error = new Status(-31008, multiLanguageMessageRepository.findByCode(-31008));
                responsePay.setError(error);
            }
        }
        return responsePay;
    }

    /**
     * http://paycom.uz/api/#merchant-api-metody-canceltransaction-otmena-finansovoj-tranzakcii
     */
    @Override
    public ResponsePay CancelTransaction(PayReq payReq) {
        ResponsePay responsePay = new ResponsePay();
        Object result;
        Status error;
        if (payReq.getParams() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getReason() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else {
            var transaction = transactionRepository.findByPaycomId(payReq.getParams().getId());
            if (transaction != null) {
                if (transaction.state == TransactionState.STATE_IN_PROGRESS) {
                    transaction.state = TransactionState.STATE_CANCELED;
                    transaction.cancelTime = new Date().getTime();
                    transaction.reason = payReq.getParams().getReason();
                    transactionRepository.save(transaction);

                    /**
                     * customerOrder save for history start
                     */
                    CustomerOrder customerOrder = transaction.getOrder();
                    customerOrder.setReason(payReq.getParams().getReason());
                    customerOrder.setCancelTime(new Date().getTime());
                    customerOrder.setState(TransactionState.STATE_CANCELED);
                    orderRepository.save(customerOrder);

                    /**
                     * customerOrder save for history finish
                     */
                    result = CancelTransactionResult.builder()
                            .cancelTime(transaction.cancelTime)
                            .transaction(transaction.id.toString())
                            .state(transaction.getState() != null ? transaction.getState().getCode() : 0)
                            .build();
                    responsePay.setResult(result);
                } else if (transaction.state == TransactionState.STATE_DONE) {
                    transaction.state = TransactionState.STATE_POST_CANCELED;
                    transaction.cancelTime = new Date().getTime();
                    transaction.reason = payReq.getParams().getReason();
                    transactionRepository.save(transaction);

                    /**
                     * customerOrder save for history start
                     */
                    CustomerOrder customerOrder = transaction.getOrder();
                    customerOrder.setReason(payReq.getParams().getReason());
                    customerOrder.setCancelTime(new Date().getTime());
                    customerOrder.setState(TransactionState.STATE_POST_CANCELED);
                    orderRepository.save(customerOrder);
                    /**
                     * customerOrder save for history finish
                     */
                    result = CancelTransactionResult.builder()
                            .cancelTime(transaction.cancelTime)
                            .transaction(transaction.id.toString())
                            .state(-2)
                            .build();
                    responsePay.setResult(result);
                } else if (transaction.state == TransactionState.STATE_POST_CANCELED) {
                    result = CancelTransactionResult.builder()
                            .cancelTime(transaction.cancelTime)
                            .transaction(transaction.id.toString())
                            .state(transaction.getState().getCode())
                            .build();
                    responsePay.setResult(result);
                } else if (transaction.state == TransactionState.STATE_CANCELED) {
                    if (transaction.getPaycomId().equals(payReq.getParams().getId())) {
                        result = CancelTransactionResult.builder()
                                .cancelTime(transaction.cancelTime)
                                .transaction(transaction.id.toString())
                                .state(transaction.getState().getCode())
                                .build();
                        responsePay.setResult(result);
                    } else {
                        error = new Status(-31007, multiLanguageMessageRepository.findByCode(-31007));
                        responsePay.setError(error);
                    }
                } else {
                    error = new Status(-31007, multiLanguageMessageRepository.findByCode(-31007));
                    responsePay.setError(error);
                }
            } else {
                error = new Status(-31003, multiLanguageMessageRepository.findByCode(-31003));
                responsePay.setError(error);
            }

        }
        return responsePay;
    }


    /**
     * http://paycom.uz/api/#merchant-api-metody-checktransaction-proverka-sostoyaniya-finansovoj-tranzakcii
     */
    @Override
    public ResponsePay CheckTransaction(PayReq payReq) {
        var responsePay = new ResponsePay();
        Object result;
        Status error;
        var transaction = transactionRepository.findByPaycomId(payReq.getParams().getId());
        if (payReq.getParams() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else if (payReq.getParams().getId() == null) {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
            return responsePay;
        } else {
            if (transaction != null) {
                if (transaction.state != null && transaction.state.equals(TransactionState.STATE_DONE)) {
                    result = CheckTransactionResult.builder()
                            .createTime(transaction.createTime != null ? transaction.createTime : 0)
                            .performTime(transaction.performTime != null ? transaction.performTime : 0)
                            .cancelTime(transaction.cancelTime != null ? transaction.cancelTime : 0)
                            .transaction(transaction.id.toString())
                            .state(2)
                            .reason(null)
                            .build();
                    responsePay.setResult(result);
                } else if (transaction.state != null && transaction.state.equals(TransactionState.STATE_CANCELED)) {
                    result = CheckTransactionResult.builder()
                            .createTime(transaction.createTime != null ? transaction.createTime : 0)
                            .performTime(transaction.performTime != null ? transaction.performTime : 0)
                            .cancelTime(transaction.cancelTime != null ? transaction.cancelTime : 0)
                            .transaction(transaction.id.toString())
                            .state(transaction.state.getCode())
                            .reason(transaction.reason.getCode() == 4 ? 3 : transaction.reason.getCode())
                            .build();
                    responsePay.setResult(result);
                } else if (transaction.state != null && transaction.state.equals(TransactionState.STATE_IN_PROGRESS)) {
                    result = CheckTransactionResult.builder()
                            .createTime(transaction.createTime != null ? transaction.createTime : 0)
                            .performTime(transaction.performTime != null ? transaction.performTime : 0)
                            .cancelTime(transaction.cancelTime != null ? transaction.cancelTime : 0)
                            .transaction(transaction.id.toString())
                            .state(transaction.state.getCode())
                            .reason(transaction.reason != null ? transaction.state.getCode() : null)
                            .build();
                    responsePay.setResult(result);
                } else if (transaction.state != null && transaction.state.equals(TransactionState.STATE_POST_CANCELED)) {
                    result = CheckTransactionResult.builder()
                            .createTime(transaction.createTime != null ? transaction.createTime : 0)
                            .performTime(transaction.performTime != null ? transaction.performTime : 0)
                            .cancelTime(transaction.cancelTime != null ? transaction.cancelTime : 0)
                            .transaction(transaction.id.toString())
                            .state(transaction.state.getCode())
                            .reason(5)
                            .build();
                    responsePay.setResult(result);
                } else {
                    result = CheckTransactionResult.builder()
                            .createTime(transaction.createTime != null ? transaction.createTime : 0)
                            .performTime(transaction.performTime != null ? transaction.performTime : 0)
                            .cancelTime(transaction.cancelTime != null ? transaction.cancelTime : 0)
                            .transaction(transaction.id.toString())
                            .state(transaction.state != null ? transaction.state.getCode() : 0)
                            .reason(transaction.reason != null ? transaction.reason.getCode() : null)
                            .build();
                    responsePay.setResult(result);
                }
            } else {
                error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
                responsePay.setError(error);
                return responsePay;
            }
            return responsePay;
        }
    }


    /**
     * http://paycom.uz/api/#merchant-api-metody-getstatement-informaciya-o-tranzakciyah-merchanta
     */
    @Override
    public ResponsePay GetStatement(PayReq payReq) {
        var resultList = new ArrayList<>();
        ResponsePay responsePay = new ResponsePay();
        Status error;
        Object result;
        ObjectNode transaction = objectMapper.createObjectNode();
        var transactions = transactionRepository.findByPaycomTimeAndState(
                payReq.getParams().getFrom(),
                payReq.getParams().getTo(),
                TransactionState.STATE_DONE);
        if (transactions != null && transaction.size() > 0) {
            for (OrderTransaction it : transactions) {
                resultList.add(
                        GetStatementResult.builder()
                                .id(it.paycomId)
                                .time(it.paycomTime)
                                .amount(it.order.amount)
                                .account(new Account(it.order.getOrderId()))
                                .createTime(it.createTime)
                                .performTime(it.performTime)
                                .cancelTime(it.cancelTime)
                                .transaction(it.id)
                                .state(it.state != null ? it.state.getCode() : 0)
                                .reason(it.reason != null ? it.reason.getCode() : 0)
                                .build());
                result = transaction.putPOJO("transactions", resultList);
                responsePay.setResult(result);
            }
        } else {
            error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
            responsePay.setError(error);
        }
        return responsePay;
    }

    private boolean checkPerformTransaction(PayReq payReq) {
        var order = orderRepository.findOne(payReq.getParams().getAccount().getOrderId());
        if (order.isEmpty()) {
            return false;
        } else return payReq.getParams().getAmount().equals(order.get().getAmount());
    }

    @Override
    public ResponsePay ChangePassword(PayReq password) {
        ResponsePay responsePay = new ResponsePay();
        Status error;
        error = new Status(-32504, multiLanguageMessageRepository.findByCode(-32504));
        responsePay.setError(error);
        return responsePay;
    }


}

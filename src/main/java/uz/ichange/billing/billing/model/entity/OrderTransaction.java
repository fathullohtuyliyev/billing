package uz.ichange.billing.billing.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.ichange.billing.billing.model.dto.OrderCancelReason;
import uz.ichange.billing.billing.model.dto.TransactionState;

@Getter
@Setter
@Entity
@Builder
public class OrderTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "paycomId")
    public String paycomId;
    @Column(name = "paycomTime")
    public Long paycomTime;
    @Column(name = "createTime")
    public Long createTime;
    @Column(name = "performTime")
    public Long performTime;
    @Column(name = "cancelTime")
    public Long cancelTime;
    @Column(name = "reason")
    public OrderCancelReason reason;
    @Column(name = "state")
    public TransactionState state;


    @OneToOne
    public CustomerOrder order;

    public OrderTransaction() {
    }


    public OrderTransaction(String paycomId, Long paycomTime, Long createTime, TransactionState state, CustomerOrder order) {
        this.paycomId = paycomId;
        this.paycomTime = paycomTime;
        this.createTime = createTime;
        this.state = state;
        this.order = order;
    }

    public OrderTransaction(Long id, String paycomId, Long paycomTime,
                            Long createTime, Long performTime,
                            Long cancelTime, OrderCancelReason reason,
                            TransactionState state,
                            CustomerOrder order) {
        this.id = id;
        this.paycomId = paycomId;
        this.paycomTime = paycomTime;
        this.createTime = createTime;
        this.performTime = performTime;
        this.cancelTime = cancelTime;
        this.reason = reason;
        this.state = state;
        this.order = order;
    }
}

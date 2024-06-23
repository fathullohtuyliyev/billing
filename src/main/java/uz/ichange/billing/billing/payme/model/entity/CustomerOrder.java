package uz.ichange.billing.billing.payme.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import uz.ichange.billing.billing.payme.model.dto.OrderCancelReason;
import uz.ichange.billing.billing.payme.model.dto.PaymentMethod;
import uz.ichange.billing.billing.payme.model.dto.TransactionState;
import uz.ichange.billing.billing.payme.model.dto.UserType;
import uz.ichange.billing.model.entity.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "customer_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String cardNumber;
    @CreatedDate
    private Date createdDate;
    private UserType userType;
    public Double amount;
    public Boolean delivered;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * unique ID,which every one Booking,Order include this id and connect this id
     */
    @Column(name = "order_id", unique = true)
    public String orderId;
    private PaymentMethod paymentMethod;

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
    public TransactionState state = TransactionState.STATE_NEW;
}

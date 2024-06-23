package uz.ichange.billing.billing.payme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ichange.billing.billing.payme.model.dto.TransactionState;
import uz.ichange.billing.billing.payme.model.entity.CustomerOrder;
import uz.ichange.billing.billing.payme.model.entity.OrderTransaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<OrderTransaction, Long> {

    @Query(value = "select c from OrderTransaction c where c.paycomId=:paycomId")
    OrderTransaction findByPaycomId(@Param("paycomId") String paycomId);

    Optional<OrderTransaction> findByOrder(CustomerOrder customerOrder);

    @Query("select o from OrderTransaction o " +
            "where o.paycomTime between ?1 and ?2 and o.state = ?3 ORDER BY o.paycomTime ASC")
    List<OrderTransaction> findByPaycomTimeAndState(Long from, Long to, TransactionState state);


}

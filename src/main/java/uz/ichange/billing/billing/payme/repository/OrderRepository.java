package uz.ichange.billing.billing.payme.repository;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ichange.billing.billing.payme.model.entity.CustomerOrder;
import uz.ichange.billing.model.entity.User;

import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query(value = "select  c from CustomerOrder c  where c.orderId=:orderId")
    Optional<CustomerOrder> findOne(@Param("orderId") String orderId);

    Page<CustomerOrder> findByUser(User user, Pageable pageable);

    Page<CustomerOrder> findAll(@NotNull Pageable pageable);

    @Query(value = "select c from CustomerOrder c where c.user.id=:userId")
    Page<CustomerOrder> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select c from CustomerOrder c where c.orderId=:orderId")
    Page<CustomerOrder> findByOrderId(@Param("orderId") Long userId, Pageable pageable);

}

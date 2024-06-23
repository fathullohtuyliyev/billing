package uz.ichange.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ichange.billing.model.entity.MultiLanguageMessage;


@Repository
public interface MultiLanguageMessageRepository extends JpaRepository<MultiLanguageMessage, Long> {
    MultiLanguageMessage findByCode(int code);
}

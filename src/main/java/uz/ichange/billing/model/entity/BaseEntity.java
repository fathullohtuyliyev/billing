package uz.ichange.billing.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @LastModifiedDate
    @Column(name = "modified_date")
    private Date modifiedDate = new Date();
    @Column(name = "modified_by_id")
    private Long modifiedById;
    @Column(name = "deleted")
    private Boolean deleted = false;
    @Column(name = "deleted_at")
    private Date deletedAt;
}


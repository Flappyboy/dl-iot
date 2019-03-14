package cn.edu.nju.software.iot.iotcore.entity.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

    @CreatedBy
    private Long createdBy;

    @CreatedDate
    private Long createdDate;

    @LastModifiedBy
    private Long LastModifiedBy;

    @LastModifiedDate
    private Long LastModifiedDate;
}

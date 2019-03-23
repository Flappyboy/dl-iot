package cn.edu.nju.software.iot.iotcore.entity;

import cn.edu.nju.software.iot.iotcore.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
public class Sensor extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String type; //

    private String state;// ON OFF

    @JsonBackReference(value = "sensors")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "deviceId", referencedColumnName = "id", nullable = false)
    private Device device;
}
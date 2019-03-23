package cn.edu.nju.software.iot.iotcore.entity;

import cn.edu.nju.software.iot.iotcore.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
public class Device extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String state; // ONLINE OFFLINE

    private String type; //Ardunio

    @JsonBackReference(value = "devices")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gatewayId", referencedColumnName = "id", nullable = false)
    private Gateway gateway;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sensor> sensors;
}
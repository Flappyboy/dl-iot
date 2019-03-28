package cn.edu.nju.software.iot.iotcore.entity;

import cn.edu.nju.software.iot.iotcore.entity.base.BaseEntity;
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
public class Gateway extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String state;// ONLINE OFFLINE

    @OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Device> devices;

    private Long lastCommunication;
}
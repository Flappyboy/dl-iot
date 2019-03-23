package cn.edu.nju.software.iot.iotcore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.DiscriminatorType.STRING;

//@DiscriminatorColumn(discriminatorType=STRING, length=10)
@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Record implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private byte[] byteValue;

    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", referencedColumnName = "id", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private Long recordTime;

    private String unit;//数据单位 如：cm, kg,

    private String mean;// 一个传感器可能传输多个值

//    @Column(nullable = false)
//    private int dataType;//数据类型 java.sql.types

    public Object getValue(){
        return byteValue;
    }
}

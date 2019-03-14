package cn.edu.nju.software.iot.iotcore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Record implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private byte[] byteValue;

    @ManyToOne
    @JoinColumn(name = "sensorId", referencedColumnName = "id", nullable = false)
    private Sensor sensor;

    @Column(nullable = false)
    private Long recordTime;

    private String unit;//数据单位 如：cm, kg,

    private String mean;// 一个传感器可能传输多个值

    @Column(nullable = false)
    private int dataType;//数据类型 java.sql.types
}

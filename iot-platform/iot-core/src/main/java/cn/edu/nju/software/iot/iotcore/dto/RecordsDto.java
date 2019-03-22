package cn.edu.nju.software.iot.iotcore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class RecordsDto {
    private String sensorId;
    private String unit;
    private String mean;
}

package cn.edu.nju.software.iot.iotcore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoubleRecordDto extends RecordDto {
    private Double value;
}

package cn.edu.nju.software.iot.iotcore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class RecordDto {
    private Long timestamp;
}

package cn.edu.nju.software.iot.iotcore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ObjectRecordDto extends RecordDto {
    private Object value;
}

package cn.edu.nju.software.iot.iotcore.dto;

import cn.edu.nju.software.iot.iotcore.entity.Record;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class OutputRecordsDto {
    List<RecordDto> records;
    String unit;
    String mean;
}

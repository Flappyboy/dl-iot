package cn.edu.nju.software.iot.iotcore.dto;

import cn.edu.nju.software.iot.iotcore.entity.DoubleRecord;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class InputRecordsDto {
    List<DoubleRecordsDto> doubleRecords;

    public List<Record> getRecords(){
        List<Record> recordList = new ArrayList<>();
        for(DoubleRecordsDto doubleRecordsDto: doubleRecords){
            recordList.addAll(doubleRecordsDto.toDoubleRecordList());
        }
        return recordList;
    }
}

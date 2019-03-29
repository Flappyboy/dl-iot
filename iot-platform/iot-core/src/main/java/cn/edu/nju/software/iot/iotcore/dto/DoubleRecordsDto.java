package cn.edu.nju.software.iot.iotcore.dto;

import cn.edu.nju.software.iot.iotcore.entity.DoubleRecord;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DoubleRecordsDto extends RecordsDto{
    private List<DoubleRecordDto> recordList;

    public List<Record> toDoubleRecordList(){
        List<Record> list = new ArrayList<>();
        for(DoubleRecordDto doubleRecordDto: recordList){
            if(doubleRecordDto == null || doubleRecordDto.getValue() == null){
                continue;
            }
            DoubleRecord doubleRecord = new DoubleRecord();
            Sensor sensor = new Sensor();
            sensor.setId(this.getSensorId());
            doubleRecord.setSensor(sensor);
            doubleRecord.setUnit(this.getUnit());
            doubleRecord.setMean(this.getMean());
            doubleRecord.setValue(doubleRecordDto.getValue());
            doubleRecord.setRecordTime(doubleRecordDto.getTimestamp());
            list.add(doubleRecord);
        }
        return list;
    }
}

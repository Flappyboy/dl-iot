package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.dto.OutputRecordsDto;
import cn.edu.nju.software.iot.iotcore.dto.RecordDto;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RecordService {
    void save(List<Record> recordList);

    List<OutputRecordsDto> findForSensorBytime(Sensor sensor, Long startTime, Long endTime, String timeUnit);

    List<OutputRecordsDto> findLast(Sensor sensor, Long last, String timeUnit);
}

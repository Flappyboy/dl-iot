package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.entity.DoubleRecord;

import java.util.List;

public interface RecordService {
    void save(List<DoubleRecord> recordList);
}

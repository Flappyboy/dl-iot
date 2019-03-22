package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.entity.Record;

import java.util.List;

public interface RecordService {
    void save(List<Record> recordList);
}

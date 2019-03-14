package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.entity.DoubleRecord;
import cn.edu.nju.software.iot.iotcore.repository.RecordRepository;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void save(List<DoubleRecord> recordList) {
        recordRepository.saveAll(recordList);
    }
}

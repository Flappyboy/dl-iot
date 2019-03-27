package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.dto.ObjectRecordDto;
import cn.edu.nju.software.iot.iotcore.dto.OutputRecordsDto;
import cn.edu.nju.software.iot.iotcore.dto.RecordDto;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.repository.RecordRepository;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void save(List<Record> recordList) {
        recordRepository.saveAll(recordList);
    }

    @Override
    public void save(List<Record> recordList, String recordWay) {
        for (Record record :
                recordList) {
            record.setRecordWay(recordWay);
        }
        save(recordList);
    }

    @Override
    public List<OutputRecordsDto> findForSensorBytime(Sensor sensor, Long startTime, Long endTime, String timeUnit) {
        if(startTime == null){
            startTime = 0l;
        }
        if(endTime == null || endTime<0){
            endTime = System.currentTimeMillis();
        }

        Record record = new Record();
        record.setSensor(sensor);

        final Long sTime = startTime;
        final Long eTime = endTime;
        Specification<Record> spec = new Specification<Record>() {
            @Override
            public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("sensor"), sensor));
                predicates.add(criteriaBuilder.between(root.get("recordTime"), sTime, eTime));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<Record> recordList = recordRepository.findAll(spec);
        return getOutputRecordsDtoList(recordList);
    }

    @Override
    public List<OutputRecordsDto> findLast(Sensor sensor, Long last, String timeUnit) {
        Long lasttime = recordRepository.findLastRecordTime(sensor);
        if(lasttime == null){
            return new ArrayList<>();
        }
        return findForSensorBytime(sensor, lasttime-last, lasttime, timeUnit);
    }

    private List<OutputRecordsDto> getOutputRecordsDtoList(List<Record> recordList){
        List<OutputRecordsDto> list = new ArrayList<>();
        Map<String, List<RecordDto>> map = new HashMap<>();
        for(Record r: recordList){
            String mean = r.getMean();
            List<RecordDto> rL = map.get(mean);
            if(rL == null){
                rL = new ArrayList<>();
                map.put(mean, rL);
                OutputRecordsDto outputRecordsDto = new OutputRecordsDto();
                outputRecordsDto.setRecords(rL);
                outputRecordsDto.setMean(mean);
                outputRecordsDto.setUnit(r.getUnit());
                list.add(outputRecordsDto);
            }
            ObjectRecordDto recordDto = new ObjectRecordDto();
            recordDto.setValue(r.getValue());
            recordDto.setTimestamp(r.getRecordTime());
            rL.add(recordDto);
        }
        return list;
    }
}

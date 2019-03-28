package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.dto.ObjectRecordDto;
import cn.edu.nju.software.iot.iotcore.dto.OutputRecordsDto;
import cn.edu.nju.software.iot.iotcore.dto.RecordDto;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.repository.RecordRepository;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import cn.edu.nju.software.iot.iotcore.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private SensorService sensorService;

    @Override
    public void save(List<Record> recordList) {
        Set<String> sensorIdSet = new HashSet<>();
        for (Record record :
                recordList) {
            if(record.getSensor() != null && StringUtils.isNotBlank(record.getSensor().getId()))
                sensorIdSet.add(record.getSensor().getId());
        }
        Iterator<String> iterator = sensorIdSet.iterator();
        while(iterator.hasNext()){
            sensorService.online(iterator.next());
        }
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
    public List<OutputRecordsDto> findForSensorBytime(Sensor sensor, Long startTime, Long endTime, Long limit, String mean) {
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
                if(StringUtils.isNotBlank(mean)){
                    predicates.add(criteriaBuilder.equal(root.get("mean"), mean));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<Record> recordList = recordRepository.findAll(spec);

        return getOutputRecordsDtoList(recordList, limit);
    }

    @Override
    public List<OutputRecordsDto> findLast(Sensor sensor, Long last, Long limit) {
        Long lasttime = recordRepository.findLastRecordTime(sensor);
        if(lasttime == null){
            return new ArrayList<>();
        }
        return findForSensorBytime(sensor, lasttime-last, lasttime, limit, null);
    }

    private List<RecordDto> filterRecords(List<RecordDto> recordDtos, Long limit){
        if(limit == null || limit <=0 || limit >= recordDtos.size()){
            return recordDtos;
        }
        double ratio = (double)limit / recordDtos.size();

        List<RecordDto> newList = new ArrayList<>();

        recordDtos.sort(new Comparator<RecordDto>() {
            @Override
            public int compare(RecordDto o1, RecordDto o2) {
                if(o1.getTimestamp() < o2.getTimestamp()){
                    return -1;
                }else if(o1.getTimestamp() > o2.getTimestamp()){
                    return 1;
                }
                return 0;
            }
        });

        for(int i=0; i<recordDtos.size(); i++) {
            if (Math.random() <= ratio) {
                newList.add(recordDtos.get(i));
            }
        }
        return newList;
    }

    private List<OutputRecordsDto> getOutputRecordsDtoList(List<Record> recordList, Long limit){
        List<OutputRecordsDto> list = new ArrayList<>();
        Map<String, List<RecordDto>> map = new HashMap<>();
        Map<String, Record> mapRecord = new HashMap<>();
        for(Record r: recordList){
            String mean = r.getMean();
            List<RecordDto> rL = map.get(mean);
            if(rL == null){
                rL = new ArrayList<>();
                map.put(mean, rL);
                mapRecord.put(mean, r);
//                OutputRecordsDto outputRecordsDto = new OutputRecordsDto();
//                outputRecordsDto.setRecords(rL);
//                outputRecordsDto.setMean(mean);
//                outputRecordsDto.setUnit(r.getUnit());
//                list.add(outputRecordsDto);
            }
            ObjectRecordDto recordDto = new ObjectRecordDto();
            recordDto.setValue(r.getValue());
            recordDto.setTimestamp(r.getRecordTime());
            rL.add(recordDto);
        }

        for (Map.Entry<String, List<RecordDto>> entry : map.entrySet()) {
            List<RecordDto> rL = filterRecords(entry.getValue(), limit);
            OutputRecordsDto outputRecordsDto = new OutputRecordsDto();
            outputRecordsDto.setRecords(rL);
            outputRecordsDto.setMean(entry.getKey());
            outputRecordsDto.setUnit(mapRecord.get(entry.getKey()).getUnit());
            list.add(outputRecordsDto);
        }

        return list;
    }
}

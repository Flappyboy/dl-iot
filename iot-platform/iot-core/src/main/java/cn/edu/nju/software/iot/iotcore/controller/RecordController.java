package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.dto.InputRecordsDto;
import cn.edu.nju.software.iot.iotcore.dto.OutputRecordsDto;
import cn.edu.nju.software.iot.iotcore.dto.RecordDto;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.repository.RecordRepository;
import cn.edu.nju.software.iot.iotcore.service.MqttGateway;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    MqttGateway mqttGateway;

    @PostMapping("/list")
    public ResponseEntity postDoubleRecords(@RequestBody InputRecordsDto recordData){
        recordService.save(recordData.getRecords());
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity getDoubleRecords(){
        return new ResponseEntity(recordRepository.findAll(), HttpStatus.CREATED);
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity getDoubleRecordsForTime(@PathVariable String id, Long startTime, Long endTime, Long limit, Long last, String mean){
        Sensor sensor = new Sensor();
        sensor.setId(id);
        List<OutputRecordsDto> result = null;
        if(startTime<0 && endTime<0 && last!=null && last>0){
            result = recordService.findLast(sensor, last, limit);
        }else{
            result = recordService.findForSensorBytime(sensor, startTime, endTime, limit, mean);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity test(String msg){

        mqttGateway.sendToMqtt("default-topic:"+msg);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}

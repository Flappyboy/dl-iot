package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.dto.AllRecordsDto;
import cn.edu.nju.software.iot.iotcore.repository.RecordRepository;
import cn.edu.nju.software.iot.iotcore.service.MqttGateway;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity postDoubleRecords(@RequestBody AllRecordsDto recordData){
        recordService.save(recordData.getRecords());
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity postDoubleRecords(){
        return new ResponseEntity(recordRepository.findAll(), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity test(String msg){

        mqttGateway.sendToMqtt("default-topic:"+msg);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}

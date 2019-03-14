package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.DoubleRecord;
import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.service.MqttGateway;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    MqttGateway mqttGateway;

    @PostMapping("/double")
    public ResponseEntity postDoubleRecords(List<DoubleRecord> records){

        recordService.save(records);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity test(String msg){

        mqttGateway.sendToMqtt("default-topic:"+msg);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}

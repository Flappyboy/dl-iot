package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    @PostMapping
    public ResponseEntity postSensor(Sensor sensor){
        sensorRepository.save(sensor);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}

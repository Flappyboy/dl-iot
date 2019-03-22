package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.repository.SensorRepository;
import cn.edu.nju.software.iot.iotcore.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorService sensorService;

    @PostMapping
    public ResponseEntity postSensor(@RequestBody Sensor sensor){
        sensorService.saveOrUpdate(sensor);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity queryDevices(){
        List<Sensor> sensorList = sensorRepository.findAll();
        return new ResponseEntity(sensorList,HttpStatus.OK);
    }

}

package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.repository.DeviceRepository;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private GatewayRepository gatewayRepository;

    @PostMapping
    public ResponseEntity postDevice(@RequestBody Device device){

        deviceRepository.save(device);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}

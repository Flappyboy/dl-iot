package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.repository.DeviceRepository;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private GatewayRepository gatewayRepository;

    @PostMapping
    public ResponseEntity postDevice(@RequestBody Device device){

        deviceService.saveOrUpdate(device);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity queryDevices(){
        List<Device> deviceList = deviceRepository.findAll();
        return new ResponseEntity(deviceList,HttpStatus.OK);
    }

}

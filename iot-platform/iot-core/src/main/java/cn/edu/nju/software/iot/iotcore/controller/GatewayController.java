package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private GatewayRepository gatewayRepository;

    @PostMapping
    public ResponseEntity postGateway(@RequestBody Gateway gateway){
        gatewayService.saveOrUpdate(gateway);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getGateway(@PathVariable String id){
        Gateway gateway = gatewayRepository.findById(id).get();
        return new ResponseEntity(gateway, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity queryDevices(){
        List<Gateway> gatewayList = gatewayRepository.findAll();
        return new ResponseEntity(gatewayList,HttpStatus.OK);
    }
}

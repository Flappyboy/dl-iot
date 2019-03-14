package cn.edu.nju.software.iot.iotcore.controller;

import cn.edu.nju.software.iot.iotcore.entity.DoubleRecord;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    @Autowired
    private GatewayRepository gatewayRepository;

    @PostMapping
    public ResponseEntity postGateway(@RequestBody Gateway gateway){
        gatewayRepository.save(gateway);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getGateway(@PathVariable String id){
        Gateway gateway = gatewayRepository.findById(id).get();
        return new ResponseEntity(gateway, HttpStatus.CREATED);
    }

}

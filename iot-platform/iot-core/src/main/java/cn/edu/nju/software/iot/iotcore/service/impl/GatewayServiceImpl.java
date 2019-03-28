package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.entity.base.State;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.DeviceService;
import cn.edu.nju.software.iot.iotcore.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {
    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceService deviceService;

    @Scheduled(fixedDelay = 5000)
    public void checkOFFLINE(){
        List<Gateway> gatewayList = gatewayRepository.findAll();
        for (Gateway gateway:
                gatewayList) {
            if(gateway.getLastCommunication() == null){
                gateway.setLastCommunication(0l);
                gatewayRepository.saveAndFlush(gateway);
                continue;
            }
            if(System.currentTimeMillis() - gateway.getLastCommunication() > 10 * 1000){
                if(gateway.getState().equals(State.ONLINE.toString()))
                    offline(gateway);
            }
        }
//        System.out.println("Gateway check OFFLINE!!!-----------------------------------------");
    }

    @Override
    public Gateway saveOrUpdate(Gateway gateway) {
        gateway.setLastCommunication(System.currentTimeMillis());
        if(gateway.getState().equals(State.OFFLINE.toString())){
            offline(gateway);
            return gateway;
        }
        return gatewayRepository.saveAndFlush(gateway);
    }

    @Override
    public void offline(Gateway gateway) {
        if(gateway == null || StringUtils.isBlank(gateway.getId()))
            return;
        gateway = gatewayRepository.findById(gateway.getId()).get();
        for(Device device: gateway.getDevices()){
            device.setState(State.OFFLINE.toString());
            for (Sensor sensor :
                    device.getSensors()) {
                sensor.setState(State.OFFLINE.toString());
            }
        }
//        deviceService.offline(gateway.getDevices());
        gateway.setState(State.OFFLINE.toString());
        gatewayRepository.saveAndFlush(gateway);
    }

    @Override
    public void online(String gatewayId) {
        if(StringUtils.isBlank(gatewayId)){
            return;
        }
        Gateway gateway = gatewayRepository.findById(gatewayId).get();
        gateway.setState(State.ONLINE.toString());
        gateway.setLastCommunication(System.currentTimeMillis());
        gatewayRepository.saveAndFlush(gateway);
    }
}

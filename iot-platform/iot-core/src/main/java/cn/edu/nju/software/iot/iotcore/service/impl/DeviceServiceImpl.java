package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.entity.base.State;
import cn.edu.nju.software.iot.iotcore.repository.DeviceRepository;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.DeviceService;
import cn.edu.nju.software.iot.iotcore.service.GatewayService;
import cn.edu.nju.software.iot.iotcore.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Override
    public Device saveOrUpdate(Device device) {
        device.setLastCommunication(System.currentTimeMillis());
        if(device.getState().equals(State.OFFLINE.toString())){
            offline(device);
            return device;
        }
        return deviceRepository.saveAndFlush(device);
    }

    @Override
    public void offline(Device device) {
        if(device == null || StringUtils.isBlank(device.getId()))
            return;
        device = deviceRepository.findById(device.getId()).get();
        for (Sensor sensor :
                device.getSensors()) {
            sensor.setState(State.OFFLINE.toString());
        }
//        sensorService.offline(device.getSensors());
        device.setState(State.OFFLINE.toString());
        deviceRepository.saveAndFlush(device);
    }

    @Override
    public void offline(List<Device> deviceList) {
        for (Device device :
                deviceList) {
            offline(device);
        }
    }


    @Override
    public List<Device> findByGateway(Gateway gateway) {

        if(gateway==null || StringUtils.isBlank(gateway.getId()))
            return new ArrayList();
        List list = gatewayRepository.findById(gateway.getId()).get().getDevices();
        return list;
    }

    @Override
    public void online(String deviceId) {
        if(StringUtils.isBlank(deviceId)){
            return;
        }
        Device device = deviceRepository.findById(deviceId).get();

        // gatewayService.online(device.getGateway().getId());
        device.setState(State.ONLINE.toString());
        device.setLastCommunication(System.currentTimeMillis());
        deviceRepository.save(device);
    }
}

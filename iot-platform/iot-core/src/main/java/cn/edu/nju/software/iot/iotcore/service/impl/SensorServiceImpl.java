package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.entity.base.State;
import cn.edu.nju.software.iot.iotcore.repository.DeviceRepository;
import cn.edu.nju.software.iot.iotcore.repository.SensorRepository;
import cn.edu.nju.software.iot.iotcore.service.DeviceService;
import cn.edu.nju.software.iot.iotcore.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Sensor saveOrUpdate(Sensor sensor) {
        sensor.setLastCommunication(System.currentTimeMillis());
        if(sensor.getState().equals(State.OFFLINE.toString())){
            offline(sensor);
            return sensor;
        }
        return sensorRepository.saveAndFlush(sensor);
    }

    @Override
    public void offline(Sensor sensor) {
        if(sensor == null || StringUtils.isBlank(sensor.getId()))
            return;
        sensor = sensorRepository.findById(sensor.getId()).get();
        sensor.setState(State.OFFLINE.toString());
        sensorRepository.saveAndFlush(sensor);
    }

    @Override
    public void offline(List<Sensor> sensorList) {
        for (Sensor sensor :
                sensorList) {
            offline(sensor);
        }
    }

    @Override
    public List<Sensor> findByDevice(Device device) {
        if(device==null || StringUtils.isBlank(device.getId()))
            return new ArrayList();
        List<Sensor> list = deviceRepository.findById(device.getId()).get().getSensors();
        return list;
    }

    @Override
    public void online(String sensorId) {
        if(StringUtils.isBlank(sensorId)){
            return;
        }
        Sensor sensor = sensorRepository.findById(sensorId).get();
        Device device = sensor.getDevice();
        deviceService.online(device.getId());
        sensor.setState(State.ONLINE.toString());
        sensor.setLastCommunication(System.currentTimeMillis());
        sensorRepository.save(sensor);
    }
}

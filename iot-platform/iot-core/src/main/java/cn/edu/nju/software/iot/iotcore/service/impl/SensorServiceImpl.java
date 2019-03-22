package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.repository.DeviceRepository;
import cn.edu.nju.software.iot.iotcore.repository.SensorRepository;
import cn.edu.nju.software.iot.iotcore.service.DeviceService;
import cn.edu.nju.software.iot.iotcore.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public Sensor saveOrUpdate(Sensor sensor) {
        return sensorRepository.saveAndFlush(sensor);
    }
}

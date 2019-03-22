package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;

public interface SensorService {
    Sensor saveOrUpdate(Sensor sensor);
}

package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;

import java.util.List;

public interface SensorService {
    Sensor saveOrUpdate(Sensor sensor);

    void offline(Sensor sensor);

    void offline(List<Sensor> sensorList);

    List<Sensor> findByDevice(Device device);

    void online(String sensorId);
}

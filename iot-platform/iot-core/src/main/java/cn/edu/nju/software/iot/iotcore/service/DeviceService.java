package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;

import java.util.List;

public interface DeviceService {
    Device saveOrUpdate(Device device);
    void offline(Device device);
    void offline(List<Device> deviceList);

    List<Device> findByGateway(Gateway gateway);

    void online(String deviceId);
}

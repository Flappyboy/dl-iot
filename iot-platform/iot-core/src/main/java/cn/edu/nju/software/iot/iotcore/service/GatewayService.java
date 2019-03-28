package cn.edu.nju.software.iot.iotcore.service;

import cn.edu.nju.software.iot.iotcore.entity.Gateway;

public interface GatewayService {
    Gateway saveOrUpdate(Gateway gateway);

    void offline(Gateway gateway);

    void online(String gatewayId);
}

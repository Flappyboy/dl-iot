package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GatewayServiceImpl implements GatewayService {
    @Autowired
    private GatewayRepository gatewayRepository;


    @Override
    public Gateway saveOrUpdate(Gateway gateway) {
        return gatewayRepository.saveAndFlush(gateway);
    }
}

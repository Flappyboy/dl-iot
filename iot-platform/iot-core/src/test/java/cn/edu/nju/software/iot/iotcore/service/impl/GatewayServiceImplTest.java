package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.IotCoreApplication;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.entity.base.State;
import cn.edu.nju.software.iot.iotcore.service.GatewayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IotCoreApplication.class)
public class GatewayServiceImplTest {
    @Autowired
    private GatewayService gatewayService;

    @Test
    public void saveOrUpdate() {
        Gateway gateway = new Gateway();
        gateway.setId("GateWay_1");
        gateway.setState(State.OFFLINE.toString());
        gatewayService.saveOrUpdate(gateway);
    }

    @Test
    public void offline() {
    }
}
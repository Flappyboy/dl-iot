package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.IotCoreApplication;
import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import cn.edu.nju.software.iot.iotcore.repository.GatewayRepository;
import cn.edu.nju.software.iot.iotcore.service.DeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IotCoreApplication.class)
public class DeviceServiceImplTest {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private GatewayRepository gatewayRepository;

    @Test
    public void saveOrUpdate() {
    }

    @Test
    public void offline() {
    }

    @Test
    public void findByGateway() {
        Gateway gateway = gatewayRepository.findById("GateWay_1").get();
        List<Device> deviceList = deviceService.findByGateway(gateway);
        for (Device device :
                deviceList) {
            System.out.println(device.getId());
        }
    }
}
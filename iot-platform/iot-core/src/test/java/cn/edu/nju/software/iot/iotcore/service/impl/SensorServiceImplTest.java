package cn.edu.nju.software.iot.iotcore.service.impl;

import cn.edu.nju.software.iot.iotcore.IotCoreApplication;
import cn.edu.nju.software.iot.iotcore.entity.Device;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import cn.edu.nju.software.iot.iotcore.service.SensorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IotCoreApplication.class)
public class SensorServiceImplTest {
    @Autowired
    private SensorService sensorService;

    @Test
    public void saveOrUpdate() {
    }

    @Test
    public void offline() {
    }

    @Test
    public void offline1() {
    }

    @Test
    public void findByDevice() {
        Device device = new Device();
        device.setId("arduino_0");
        List<Sensor> sensorList = sensorService.findByDevice(device);
        for (Sensor sensor :
                sensorList) {
            System.out.println(sensor.getId());
        }
    }
}
package cn.edu.nju.software.iot.iotcore.repository;

import cn.edu.nju.software.iot.iotcore.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}


package cn.edu.nju.software.iot.iotcore.repository;

import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, String> {
}


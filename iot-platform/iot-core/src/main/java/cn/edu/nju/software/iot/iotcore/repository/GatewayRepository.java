package cn.edu.nju.software.iot.iotcore.repository;

import cn.edu.nju.software.iot.iotcore.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayRepository extends JpaRepository<Gateway, String> {
}


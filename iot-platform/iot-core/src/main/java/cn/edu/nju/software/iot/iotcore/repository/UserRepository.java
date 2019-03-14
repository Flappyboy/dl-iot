package cn.edu.nju.software.iot.iotcore.repository;

import cn.edu.nju.software.iot.iotcore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}


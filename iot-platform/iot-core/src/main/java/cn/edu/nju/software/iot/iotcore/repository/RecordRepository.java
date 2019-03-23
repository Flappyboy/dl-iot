package cn.edu.nju.software.iot.iotcore.repository;

import cn.edu.nju.software.iot.iotcore.entity.Record;
import cn.edu.nju.software.iot.iotcore.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {

}


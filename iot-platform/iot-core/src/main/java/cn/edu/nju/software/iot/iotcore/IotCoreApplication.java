package cn.edu.nju.software.iot.iotcore;

import cn.edu.nju.software.iot.iotcore.config.MqttProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties(MqttProperties.class)
@SpringBootApplication
public class IotCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotCoreApplication.class, args);
    }

}

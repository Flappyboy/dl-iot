package cn.edu.nju.software.iot.iotcore.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    private MqttInboundProperties inbound;
    private MqttOutboundProperties outbound;
}

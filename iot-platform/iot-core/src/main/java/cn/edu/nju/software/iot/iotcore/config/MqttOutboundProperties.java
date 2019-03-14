package cn.edu.nju.software.iot.iotcore.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MqttOutboundProperties {
    private String urls;
    private String username;
    private String password;
    private String clientId;
    private String topic;
}

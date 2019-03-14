package cn.edu.nju.software.iot.iotcore.config;

import lombok.Data;

@Data
public class MqttInboundProperties {
    private String url;
    private String username;
    private String password;
    private String clientId;
    private String topics;
}

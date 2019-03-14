package cn.edu.nju.software.iot.iotcore.mqtt;

import cn.edu.nju.software.iot.iotcore.config.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
@Configuration
public class MqttOutboundConfiguration {
    @Autowired
    private MqttProperties mqttProperties;
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        String[] serverURIs = mqttProperties.getOutbound().getUrls().split(",");
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        factory.setServerURIs("tcp://192.168.10.100:1883", "tcp://host2:1883");
        factory.setServerURIs(serverURIs);
        factory.setCleanSession(false);
//        factory.setUserName("username");
//        factory.setPassword("password");
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(mqttProperties.getOutbound().getClientId(), mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttProperties.getOutbound().getTopic());
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}

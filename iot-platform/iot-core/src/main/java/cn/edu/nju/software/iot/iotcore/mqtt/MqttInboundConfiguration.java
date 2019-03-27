package cn.edu.nju.software.iot.iotcore.mqtt;
import cn.edu.nju.software.iot.iotcore.config.MqttProperties;
import cn.edu.nju.software.iot.iotcore.dto.InputRecordsDto;
import cn.edu.nju.software.iot.iotcore.service.RecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
@Slf4j
public class MqttInboundConfiguration {

    @Autowired
    private MqttProperties mqttProperties;
    @Autowired
    private RecordService recordService;
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageProducer inbound(MqttPahoClientFactory mqttPahoClientFactory) {
        String[] inboundTopics = mqttProperties.getInbound().getTopics().split(",");
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getInbound().getUrl(), mqttProperties.getInbound().getClientId(),
                        mqttPahoClientFactory,inboundTopics);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                InputRecordsDto inputRecordsDto = JSONObject.parseObject((String)message.getPayload(), InputRecordsDto.class);
                recordService.save(inputRecordsDto.getRecords(), "mqtt");
            }
        };
    }
}
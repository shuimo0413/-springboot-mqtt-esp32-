package mqttesp8266.demos.web.service;

import mqttesp8266.demos.web.geteway.MqttSendGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqttMessageService {

    @Qualifier("mqttSendChannel")
//    @Autowired
//    private MessageChannel mqttSendChannel;

    public void handleDeviceMessage(String topic, String payload) {
        System.out.println("收到MQTT消息 | 主题: " + topic + ", 内容: " + payload);
        // TODO: 在这里写你的业务逻辑
    }
//   发送数据

//    @Resource
//    private MqttSendGateway mqttSendGateway;
//    public void sendToDefaultTopic(String topic,String payload) {
//        mqttSendChannel.send(
//                MessageBuilder.withPayload(payload)
//                        .setHeader("mqtt_topic", topic)
//                        .build());
//    }


}
package mqttesp8266.demos.web.geteway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttSendChannel")
public interface MqttSendGateway {

    /**
     * 发送消息到默认主题
     */
    void sendToDefaultTopic(String payload);

    /**
     * 发送消息到指定主题
     */
    void sendToSpecifiedTopic(@Header(MqttHeaders.TOPIC) String topic, String payload);
}
package mqttesp8266.demos.web.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MqttConfig {

    @Value("${mqtt.broker}")
    private String broker;
    @Value("${mqtt.username}")
    private String username;
    @Value("${mqtt.password}")
    private String password;
    @Value("${mqtt.client-id}")
    private String clientId;
    @Value("${mqtt.default-topic}")
    private String defaultTopic;
    @Value("${mqtt.qos}")
    private int qos;
    @Value("${mqtt.timeout}")
    private int timeout;
    @Value("${mqtt.keepalive}")
    private int keepalive;

    @Resource
    private mqttesp8266.demos.web.service.MqttMessageService mqttMessageService;

    /**
     * MQTT连接工厂
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{broker});
        if (username != null && !username.isEmpty()) {
            options.setUserName(username);
        }
        if (password != null && !password.isEmpty()) {
            options.setPassword(password.toCharArray());
        }
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepalive);
        options.setCleanSession(false);
        factory.setConnectionOptions(options);
        return factory;
    }

    // ========== 发送消息配置 ==========
    @Bean
    public MessageChannel mqttSendChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttSendChannel")
    public MessageHandler mqttSendHandler() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(clientId + "_send", mqttClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic(defaultTopic);
        handler.setDefaultQos(qos);
        handler.setConverter(new DefaultPahoMessageConverter());
        return handler;
    }

    // ========== 接收消息配置 ==========
    @Bean
    public MessageChannel mqttReceiveChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttReceiveAdapter() {
        log.info("正在订阅 MQTT 主题: {}", defaultTopic);
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                clientId + "_receive",  // 避免与发送端ID冲突
                mqttClientFactory(),
                defaultTopic
        );
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(qos);
        adapter.setOutputChannel(mqttReceiveChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttReceiveChannel")
    public MessageHandler mqttReceiveHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            String payload = message.getPayload().toString();
            log.info(" 收到 MQTT 消息 | 主题: {} | 内容: {}", topic, payload);
            // 调用 Service 处理消息
            mqttMessageService.handleDeviceMessage(topic, payload);
        };
    }
}
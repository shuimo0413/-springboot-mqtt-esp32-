package mqttesp8266.demos.web.dto;

import lombok.Data;

// 用 Lombok 的 @Data 自动生成 getter/setter，不用手动写
@Data
public class MqttSendDto {
    // 要发送的 MQTT 主题（默认给 ESP8266 订阅的 esp8266/recv）
    private String topic = "esp8266/recv";
    // 要发送给 ESP8266 的具体消息内容（必填）
    private String message;
}
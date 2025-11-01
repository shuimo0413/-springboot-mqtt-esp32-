package mqttesp8266.demos.web.controller;

import lombok.extern.slf4j.Slf4j;
import mqttesp8266.demos.web.dto.MqttSendDto;
import mqttesp8266.demos.web.geteway.MqttSendGateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mqtt")
@Slf4j
public class MqttTestController {

    @Resource
    private MqttSendGateway mqttSendGateway;

    /**
     * 发送到默认主题
     */
//    @GetMapping("/send/default")
//    public String sendToDefaultTopic(@RequestParam String msg) {
//        try {
//            mqttSendGateway.sendToDefaultTopic(msg);
//            return "已发送到默认主题: " + msg;
//        } catch (Exception e) {
//            return "发送失败: " + e.getMessage();
//        }
//    }
//
//    /**
//     * 发送到指定主题
//     */
//    @GetMapping("/send/topic")
//    public String sendToSpecifiedTopic(@RequestParam String topic, @RequestParam String msg) {
//        try {
//            mqttSendGateway.sendToSpecifiedTopic(topic, msg);
//            return "已发送到主题【" + topic + "】: " + msg;
//        } catch (Exception e) {
//            return "发送失败: " + e.getMessage();
//        }
//    }


    @PostMapping("/send")
    public void sendToSpecifiedTopic(@RequestBody MqttSendDto dto) {
        log.info("前端发送了信息: {}", dto);
        mqttSendGateway.sendToSpecifiedTopic(dto.getTopic(), dto.getMessage());
    }
}
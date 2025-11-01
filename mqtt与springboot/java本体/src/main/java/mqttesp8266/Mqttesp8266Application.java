package mqttesp8266;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration; // 导入这个类
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 排除数据源自动配置
@EnableIntegration
public class Mqttesp8266Application {
    public static void main(String[] args) {
        SpringApplication.run(Mqttesp8266Application.class, args);
    }
}




package egovframework.com.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableRabbit
public class EgovBoardRabbitMqConnection {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Value("${opensearch.synclog.enabled}")
    private boolean synclogEnabled;

    @Bean
    public CachingConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);

        try {
            if (synclogEnabled) {
                connectionFactory.createConnection();
                log.info("RabbitMQ connection established successfully.");
            } else {
                log.info("Opensearch connection disabled");
            }
        } catch (AmqpConnectException e) {
            log.warn("RabbitMQ connection failed : {}", e.getMessage()); // warn으로 로그 레벨 조정
        } catch (Exception e) {
            log.error("Unknown error connecting to RabbitMQ : {}", e.getMessage());
        }

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 🔹 메시지 전송 실패 시 예외 핸들링
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.error("⚠️ Message returned: {}, Exchange: {}, RoutingKey: {}", replyText, exchange, routingKey)
        );

        return rabbitTemplate;
    }
}

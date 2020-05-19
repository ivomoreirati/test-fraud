package br.com.itau.fraud.config;

import br.com.itau.fraud.util.TransactionQueueConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class AppConfig {

    @Bean
    public TopicExchange transactionFraudExchange() {
        return new TopicExchange(TransactionQueueConfig.TRANSACTION_FRAUD_EXCHANGE);
    }

    @Bean
    public Binding transactionApplyRulesBinding(TopicExchange transactionExchange, Queue transactionQueue) {
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with(
                TransactionQueueConfig.TRANSACTION_APPLY_RULES_ROUTING_KEY);
    }

    @Bean
    public Queue transactionApplyRulesQueue() {
        return QueueBuilder.durable(TransactionQueueConfig.TRANSACTION_APPLY_RULES_QUEUE_NAME).build();
    }

}
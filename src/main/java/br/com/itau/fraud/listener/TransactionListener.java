package br.com.itau.fraud.listener;

import br.com.itau.fraud.exceptions.TransactionDataException;
import br.com.itau.fraud.util.TransactionQueueConfig;
import br.com.itau.fraud.processor.IProcessor;
import br.com.itau.fraud.processor.MessagesTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class TransactionListener {

    private static final String MESSAGE_ID = "id";

    private static final String MESSAGE_TYPE_HEADER_KEY = "messageType";

    @Autowired
    private List<IProcessor> processors;

    @RabbitListener(queues = TransactionQueueConfig.TRANSACTION_APPLY_RULES_QUEUE_NAME, concurrency = "1-50")
    public void transactionsReceiver(final Message message,
                                 @Header(value = MESSAGE_TYPE_HEADER_KEY, required = false)
                                         String messageType,
                                 @Header(value = MESSAGE_ID, required = false) String messageId)
            throws Exception {

        log.debug("Transaction receiver message id [{}].", messageId);
        receiver(messageType, message);
    }

    private void receiver(final String messageType, final Message message) throws Exception {

        if (Arrays.stream(MessagesTypeEnum.values()).noneMatch(v -> v.getValue().equals(messageType))) {
            log.warn("[WARNING] Invalid message type: " + messageType);
            return;
        }

        IProcessor processor = getProcessor(MessagesTypeEnum.valueOf(messageType.toUpperCase()));

        if (Objects.nonNull(processor)) {
            processor.processMessage(new String(message.getBody(), StandardCharsets.UTF_8));
        }
    }

    private IProcessor getProcessor(final MessagesTypeEnum messageType) {
        return processors.stream().filter(iProcessor -> iProcessor.getMessageStatus().equals(messageType)).findFirst()
                .orElseThrow(TransactionDataException::new);
    }
}
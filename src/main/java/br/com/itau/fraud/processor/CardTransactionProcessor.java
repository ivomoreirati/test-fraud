package br.com.itau.fraud.processor;

import br.com.itau.fraud.dtos.IBaseDTO;
import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.services.FraudService;
import br.com.itau.fraud.util.TransactionQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardTransactionProcessor extends BaseProcessor<TransactionDTO> {

    @Autowired
    private FraudService fraudService;

    @Override
    protected void doProcessMessage(TransactionDTO dto) {
        fraudService.process(dto);
    }

    @Override
    protected String getMessageType() {
        return TransactionQueueConfig.CARD_MESSAGE_TYPE;
    }

    @Override
    public Class<TransactionDTO> getClassDtoClass() {
        return TransactionDTO.class;
    }

    @Override
    protected boolean isMessageValid(final IBaseDTO dto) {
        // TODO validate dto
        return true;
    }

    @Override
    public MessagesTypeEnum getMessageStatus() {
        return MessagesTypeEnum.CARD;
    }
}
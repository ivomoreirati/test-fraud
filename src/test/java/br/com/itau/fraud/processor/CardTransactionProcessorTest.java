package br.com.itau.fraud.processor;

import br.com.itau.fraud.dtos.TransactionDTO;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CardTransactionProcessorTest extends BaseProcessorTest {

    @SpyBean
    private CardTransactionProcessor cardTransactionProcessor;

    @Test
    public void processMessageTestValidMessageProcessDto() throws Exception {
        final var transactionDTO = TransactionDTO.builder().id(BigInteger.ONE).user("TESTE").build();
        String message = objectMapper.writeValueAsString(transactionDTO);

        cardTransactionProcessor.processMessage(message);

        verify(cardTransactionProcessor, times(1)).doProcessMessage(any(TransactionDTO.class));
    }

    @Test
    public void processMessageTestEmptyMessageDoNotProcessDto() {
        cardTransactionProcessor.processMessage("");
        verify(cardTransactionProcessor, times(0)).doProcessMessage(any(TransactionDTO.class));
    }

    @Test(expected = NullPointerException.class)
    public void processMessageTestNullMessageNullPointer() {
        cardTransactionProcessor.processMessage(null);
    }
}
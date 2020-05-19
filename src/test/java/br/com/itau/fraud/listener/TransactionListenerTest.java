package br.com.itau.fraud.listener;

import br.com.itau.fraud.base.BaseTest;
import br.com.itau.fraud.processor.CardTransactionProcessor;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TransactionListenerTest extends BaseTest {

    @SpyBean
    private TransactionListener transactionListener;

    @SpyBean
    private CardTransactionProcessor cardTransactionProcessor;

    private Message mockMessage = new Message("rabbit message".getBytes(), null);

    @Test
    public void cardTransactionReceiverTestSuccess() throws Exception {
        transactionListener.transactionsReceiver(mockMessage, "card", "123");

        verify(cardTransactionProcessor).processMessage(new String(mockMessage.getBody(),
                StandardCharsets.UTF_8));
    }

    @Test
    public void cardTransactionReceiverTestWrongType() throws Exception {
        transactionListener.transactionsReceiver(mockMessage, "wrongType", "123");

        verifyZeroInteractions(cardTransactionProcessor);
    }

}
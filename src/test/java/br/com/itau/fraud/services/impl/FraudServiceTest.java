package br.com.itau.fraud.services.impl;

import br.com.itau.fraud.base.BaseTest;
import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.models.Transaction;
import br.com.itau.fraud.repositories.TransactionRepository;
import br.com.itau.fraud.services.FraudService;
import br.com.itau.fraud.services.valitations.transaction.CheckTransactionHighFrequencyValidation;
import br.com.itau.fraud.services.valitations.transaction.CheckTransactionNotExistsValidation;
import br.com.itau.fraud.services.valitations.transaction.CheckTransactionValidation;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class FraudServiceTest extends BaseTest {

    private FraudService fraudService;

    @Mock
    TransactionRepository transactionRepository;

    List<CheckTransactionValidation> validations = Lists.newArrayList(new CheckTransactionHighFrequencyValidation(), new CheckTransactionNotExistsValidation());

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.fraudService = new FraudServiceImpl(transactionRepository, validations);
    }

    @Test
    public void testProcessWithViolation() {
        final var transactionDTO = TransactionDTO.builder().id(BigInteger.ONE).user("TESTE").build();
        final var transaction = Transaction.builder().build();

        doReturn(transaction).when(transactionRepository).findById(any());

        fraudService.process(transactionDTO);

        assertTrue(this.validations.stream().anyMatch(checkTransactionValidation ->
                checkTransactionValidation.valid(any(), any(), any()).equals(CompletableFuture.completedFuture(true))));

    }

    @Test
    public void testProcessWithoutViolation() {
        final var transactionDTO = TransactionDTO.builder().id(BigInteger.ONE).user("TESTE").build();
        final var transaction = Transaction.builder().id(BigInteger.ONE).user("TESTE").build();

        doReturn(transaction).when(transactionRepository).findById(any());

        fraudService.process(transactionDTO);

        assertTrue(this.validations.stream().allMatch(checkTransactionValidation ->
                checkTransactionValidation.valid(any(), any(), any()).equals(CompletableFuture.completedFuture(false))));
    }
}
package br.com.itau.fraud.services.validations.transaction;

import br.com.itau.fraud.base.BaseTest;
import br.com.itau.fraud.constants.Violation;
import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.models.Transaction;
import br.com.itau.fraud.repositories.TransactionRepository;
import br.com.itau.fraud.services.FraudService;
import br.com.itau.fraud.services.impl.FraudServiceImpl;
import br.com.itau.fraud.services.valitations.transaction.CheckTransactionNotExistsValidation;
import br.com.itau.fraud.services.valitations.transaction.CheckTransactionValidation;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class CheckAccountNotCreatedValidationTest  extends BaseTest {

    @InjectMocks
    private CheckTransactionNotExistsValidation validation;

    private FraudService fraudService;

    @Mock
    TransactionRepository transactionRepository;

    List<CheckTransactionValidation> validations = Lists.newArrayList(new CheckTransactionNotExistsValidation());

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.fraudService = new FraudServiceImpl(transactionRepository, validations);
    }

    @Test
    public void testCheckTransactionNotExistsValidationWithViolation() {
        final var transactionDTO = TransactionDTO.builder().id(BigInteger.ONE).user("TESTE").build();
        final var transaction = Transaction.builder().build();
        doReturn(transaction).when(transactionRepository).findById(any());

        Set<String> violations = Sets.newHashSet();
        validation.valid(fraudService, transactionDTO, violations);
        String violation = Violation.Transaction.HIGH_FREQUENCY_SMALL_INTERVAL;
        assertTrue(violations.contains(violation));
    }

    @Test
    public void testCheckTransactionNotExistsValidationWithoutViolation() throws IOException, JSONException {
        final var transactionDTO = TransactionDTO.builder().id(BigInteger.ONE).user("TESTE").build();
        final var transaction = Transaction.builder().id(BigInteger.ONE).user("TESTE").build();
        doReturn(transaction).when(transactionRepository).findById(any());

        Set<String> violations = Sets.newHashSet();
        validation.valid(fraudService, transactionDTO, violations);
        assertEquals(0, violations.size());
    }
}
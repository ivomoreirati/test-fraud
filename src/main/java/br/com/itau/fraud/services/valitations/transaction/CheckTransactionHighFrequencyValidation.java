package br.com.itau.fraud.services.valitations.transaction;

import br.com.itau.fraud.constants.Violation;
import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.services.FraudService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class CheckTransactionHighFrequencyValidation implements CheckTransactionValidation {

    @Override
    public CompletableFuture<Boolean> valid(FraudService fraudService, TransactionDTO transaction, Set<String> violations) {
        violations.add(Violation.Transaction.HIGH_FREQUENCY_SMALL_INTERVAL);
        return CompletableFuture.completedFuture(true);
    }
}
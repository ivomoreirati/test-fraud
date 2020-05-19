package br.com.itau.fraud.services.valitations.transaction;

import br.com.itau.fraud.constants.Violation;
import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.services.FraudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.isNull;

@Service
public class CheckTransactionNotExistsValidation implements CheckTransactionValidation {

    @Autowired
    FraudService fraudService;

    @Override
    public CompletableFuture<Boolean> valid(FraudService fraudService, TransactionDTO transaction, Set<String> violations) {
        if(isNull(fraudService.findTransactionById(transaction.getId()))){
            violations.add(Violation.Transaction.TRANSACTION_NOT_EXISTS);
            CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.completedFuture(true);
    }
}
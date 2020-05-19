package br.com.itau.fraud.services.valitations.transaction;

import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.services.FraudService;
import org.springframework.scheduling.annotation.Async;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface CheckTransactionValidation {

	@Async
	CompletableFuture<Boolean> valid(final FraudService service, final TransactionDTO transaction, final Set<String> violations);
}

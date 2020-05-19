package br.com.itau.fraud.services.impl;

import br.com.itau.fraud.models.Transaction;
import br.com.itau.fraud.services.valitations.transaction.CheckTransactionValidation;
import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.repositories.TransactionRepository;
import br.com.itau.fraud.services.FraudService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class FraudServiceImpl implements FraudService {

    private TransactionRepository repository;

    private final List<CheckTransactionValidation> validations;

    public List<CheckTransactionValidation> getValidations() {
        return validations;
    }

    public FraudServiceImpl(TransactionRepository repository, List<CheckTransactionValidation> validations) {
        this.repository = repository;
        this.validations = validations;
    }

    public Transaction findTransactionById(BigInteger id){
        return repository.findById(id).orElse(null);
    }

    @Override
    public void process(final TransactionDTO transaction) {
        final Set<String> violations = Sets.newHashSet();
        final var futures = getValidations().stream()
                .map(it -> it.valid(this, transaction, violations))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();


        //Passar o violations para uma outra fila para processar o proximo passo descrito (Verificar listas restritivas, por exemplo)
        log.info(violations.toString());
    }
}

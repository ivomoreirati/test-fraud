package br.com.itau.fraud.services;

import br.com.itau.fraud.models.Transaction;
import br.com.itau.fraud.dtos.TransactionDTO;

import java.math.BigInteger;

public interface FraudService {

    Transaction findTransactionById(BigInteger id);

    void process(final TransactionDTO transaction);
}

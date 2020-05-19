package br.com.itau.fraud.repositories;

import br.com.itau.fraud.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, BigInteger> {

    Optional<Transaction> findById(BigInteger id);

}
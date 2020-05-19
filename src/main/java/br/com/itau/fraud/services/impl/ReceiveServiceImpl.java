package br.com.itau.fraud.services.impl;

import br.com.itau.fraud.dtos.TransactionDTO;
import br.com.itau.fraud.services.BaseProcessorService;
import br.com.itau.fraud.services.FraudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ReceiveServiceImpl extends BaseProcessorService<TransactionDTO> {

    @Autowired
    private FraudService fraudService;

    @Override
    @Transactional
    public void process(TransactionDTO transactionDTO) {

        long startTime = System.currentTimeMillis();
        log.info("Started processing transaction " + transactionDTO.getId());
        fraudService.process(transactionDTO);
        long timeElapsed = System.currentTimeMillis() - startTime;
        log.info("Processed transaction code: " + transactionDTO.getId() + "  in "
                + timeElapsed / 1000 + " seconds");
    }
}
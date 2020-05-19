package br.com.itau.fraud.processor;

import br.com.itau.fraud.base.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseProcessorTest extends BaseTest {

    @Autowired
    protected ObjectMapper objectMapper;

}
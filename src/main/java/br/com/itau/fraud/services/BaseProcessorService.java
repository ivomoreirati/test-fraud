package br.com.itau.fraud.services;

import br.com.itau.fraud.dtos.IBaseDTO;

public abstract class BaseProcessorService<T extends IBaseDTO> {

    protected abstract void process(T dto);

}
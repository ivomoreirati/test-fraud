package br.com.itau.fraud.processor;

public interface IProcessor {

	void processMessage(final String message) throws Exception;

	MessagesTypeEnum getMessageStatus();

}
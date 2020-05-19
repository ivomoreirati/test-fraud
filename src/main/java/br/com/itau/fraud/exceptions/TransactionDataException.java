package br.com.itau.fraud.exceptions;

public class TransactionDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public TransactionDataException() {
        super();
    }

    public TransactionDataException(String msg) {
	    super(msg);
    }
}
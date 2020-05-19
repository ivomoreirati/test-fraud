package br.com.itau.fraud.util;

public class TransactionQueueConfig {

    public static final String TRANSACTION_FRAUD_EXCHANGE = "transaction.fraud";

    public static final String TRANSACTION_APPLY_RULES_QUEUE_NAME = "apply.rules";

    public static final String TRANSACTION_APPLY_RULES_ROUTING_KEY = TRANSACTION_APPLY_RULES_QUEUE_NAME;

    public static final String WITHDRAW_MESSAGE_TYPE = "withdraw";

    public static final String CARD_MESSAGE_TYPE = "card";

    public static final String TRANSFER_MESSAGE_TYPE = "transfer";
}
package br.com.itau.fraud.processor;

public enum MessagesTypeEnum {

    CARD( "card"),
    WITHDRAW( "withdraw");

    private String value;

    MessagesTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
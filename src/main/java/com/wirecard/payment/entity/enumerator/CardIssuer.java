package com.wirecard.payment.entity.enumerator;

public enum CardIssuer {

    BANCO_DO_BRASIL("234"),
    CAIXA_ECONOMICA_FEDERAL("567"),
    UNKNOWN("000");


    private String id;

    private CardIssuer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static CardIssuer getCardIssuer(String cardNumber){
        //Mocked rule in order to showcase implementation
        String issuerNumberFromCard = cardNumber.substring(1,4);
        for(CardIssuer cardIssuer: CardIssuer.values()){
            if(cardIssuer.id.equals(issuerNumberFromCard)){
                return cardIssuer;
            }
        }
        return UNKNOWN;
    }

}

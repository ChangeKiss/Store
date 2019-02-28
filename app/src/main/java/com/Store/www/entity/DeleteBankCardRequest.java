package com.Store.www.entity;

/**
 * Created by www on 2018/4/23.
 */

public class DeleteBankCardRequest {

    /**
     * cardId : 1
     */

    private int cardId;

    public DeleteBankCardRequest(int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}

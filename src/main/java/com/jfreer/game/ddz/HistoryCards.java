package com.jfreer.game.ddz;

/**
 * User: landy
 * Date: 15/3/16
 * Time: 下午5:29
 */
public class HistoryCards {
    private int playerId;
    private byte[] cards;

    public HistoryCards() {
    }

    public HistoryCards(int playerId, byte[] cards) {
        this.playerId = playerId;
        this.cards = cards;
    }

    public boolean hasCards() {
        return cards != null && cards.length > 0;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public byte[] getCards() {
        return cards;
    }

    public void setCards(byte[] cards) {
        this.cards = cards;
    }
}

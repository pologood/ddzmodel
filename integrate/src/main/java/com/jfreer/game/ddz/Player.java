package com.jfreer.game.ddz;

import com.jfreer.game.ddz.core.Table;
import com.jfreer.game.ddz.exception.CardNotExistException;

import java.util.*;

/**
 * Created by landy on 2015/3/7.
 */
public abstract class Player {
    protected static final Random random = new Random();
    private final Integer playerId;
    private Table currentTable;
    private LinkedList<Byte> handCards = new LinkedList<Byte>();

    public Player(Integer playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "Player:" + playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Table getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(Table currentTable) {
        this.currentTable = currentTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (playerId != null ? !playerId.equals(player.playerId) : player.playerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return playerId != null ? playerId.hashCode() : 0;
    }


    public abstract void notifyCallDealer(Table table, byte orderNo);

    public void addHandCards(byte[] card) {
        for (byte one : card) {
            this.handCards.add(one);
        }
        Collections.sort(this.handCards);
    }

    public List<Byte> getHandCards() {
        return handCards;
    }

    public boolean hasCards(byte[] cards) {
        for (byte b : cards) {
            if (!handCards.contains(b)) {
                return false;
            }
        }
        return true;
    }

    public void removeCards(byte[] cards) throws CardNotExistException {
        for (Byte b : cards) {
            if (!handCards.remove(b)) {
                throw new CardNotExistException(this.toString(), b);
            }
        }
    }

    public abstract void turnToPlay(Table table, byte oldOrderNo, HistoryCards lastHistory);

    public abstract void notifyPlayedCards(HistoryCards history);


    public void afterJoinTable(Table table) {
        Log.info(String.format("%s join table %s !", this.toString(), table.getTableId()));
    }

    public abstract void afterTableFull(Table table);

    public void afterGameOver(Table table) {
        this.getHandCards().clear();
    }

    public void afterLeftTable(Table table) {
        this.currentTable = null;
        this.getHandCards().clear();
    }

}
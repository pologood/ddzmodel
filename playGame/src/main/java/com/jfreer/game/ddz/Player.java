package com.jfreer.game.ddz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: landy
 * Date: 15/3/13
 * Time: 上午11:39
 */
public class Player {
    private Set<Byte> handCards = new HashSet<Byte>();

    public boolean hasCards(byte[] cards) {
        for (byte b : cards) {
            if (!handCards.contains(b)) {
                return false;
            }
        }
        return true;
    }

    public void removeCards(byte[] cards) {
        for (byte b : cards) {
            if (!handCards.remove(b)) {
                throw new RuntimeException("删除手牌出错!" + handCards.toString() + "," + Arrays.toString(cards));
            }
        }
    }
}

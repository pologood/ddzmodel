package com.jfreer.game.ddz.player;

import com.jfreer.game.ddz.ai.CardUtils;
import com.jfreer.game.ddz.HistoryCards;
import com.jfreer.game.ddz.Player;
import com.jfreer.game.ddz.core.Table;
import com.jfreer.game.ddz.core.TableManager;
import com.jfreer.game.ddz.thread.DDZExecutor;
import com.jfreer.game.websocket.protocol.IResp;

import java.util.concurrent.TimeUnit;

/**
 * Created by landy on 2015/3/7.
 */
public class TestPlayer extends Player {

    public TestPlayer(Integer playerId, TableManager tableManager) {
        super(playerId,tableManager);
    }

    public void notifyCallDealer(final Table table, final byte orderNo) {
//        table.callDealer(TestPlayer.this, Main.TestData.poll(), orderNo);
        DDZExecutor.shortWorker().schedule(new Runnable() {
            @Override
            public void run() {
                table.callDealer(TestPlayer.this, random.nextBoolean(), orderNo);
            }
        },5,TimeUnit.SECONDS);
    }


    public void turnToPlay(final Table table, final byte oldOrderNo, final HistoryCards lastHistory) {
        /**
         * 出牌时机:为了更像真人,机器人的出牌延迟在5~15秒之间随机
         */
        int delay = random.nextInt(10) + 5;

        DDZExecutor.shortWorker().schedule(new Runnable() {
            @Override
            public void run() {
                /**
                 * 如果是主导者,则选最小的牌出
                 * 如果是跟出者,则选择最小的但是比上家大的牌出,如果没有,则不出.
                 */
                if (lastHistory == null || getPlayerId().equals(lastHistory.getPlayerId())) {
                    byte[] minCards = CardUtils.getMinCards(getHandCards());
                    table.playCards(TestPlayer.this, minCards, oldOrderNo);
                } else {
                    byte[] cardsGreaterThan = CardUtils.getCardsGreaterThan(lastHistory.getCards(), getHandCards());
                    if (cardsGreaterThan != null && cardsGreaterThan.length > 0) {
                        table.playCards(TestPlayer.this, cardsGreaterThan, oldOrderNo);
                    } else {
                        table.playNothing(TestPlayer.this, oldOrderNo);
                    }
                }
            }
        }, delay, TimeUnit.SECONDS);
    }

    public void notifyPlayedCards(HistoryCards history) {
    }


    public void afterJoinTable(Table table) {
        super.afterJoinTable(table);
        getTableManager().raiseHands(this, table.getTableId());
    }

    public void afterTableFull(Table table) {

    }

    @Override
    public void afterGameOver(Table table) {
        super.afterGameOver(table);
        getTableManager().raiseHands(this, table.getTableId());
    }

    @Override
    public void pushToClient(IResp resp) {

    }

    @Override
    public void notifyBeginPlay(int nextTablePos) {

    }

    @Override
    public void notifyCallDealer(int tablePos, int nextTablePos, int callFlag) {

    }

    @Override
    public void notifyBelowCards(int dealerPos, byte[] belowCards) {

    }

    @Override
    public void notifyPlayedCards(int tablePos, int nextTablePos, byte[] cards, HistoryCards lastHistory) {

    }

}

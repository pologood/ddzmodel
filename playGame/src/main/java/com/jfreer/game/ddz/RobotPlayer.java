package com.jfreer.game.ddz;

import java.util.concurrent.TimeUnit;

/**
 * Created by landy on 2015/3/16.
 */
public class RobotPlayer extends Player {

    public RobotPlayer(int playerId) {
        super(playerId);
    }

    @Override
    public void turnToPlay(final Table table, final byte oldOrderNo, final PlayedCards lastHistory) {
        /**
         * 出牌时机:为了更像真人,机器人的出牌延迟在5~15秒之间随机
         */
        int delay = random.nextInt(10) + 5;

        DDZThreadPoolExecutor.INSTANCE.schedule(new Runnable() {
            @Override
            public void run() {
                /**
                 * 如果是主导者,则选最小的牌出
                 * 如果是跟出者,则选择最小的但是比上家大的牌出,如果没有,则不出.
                 */
                if (lastHistory == null || getPlayerId().equals(lastHistory.getPlayerId())) {
                    byte[] minCards = CardUtils.getMinCards(getHandCards());
                    table.playCards(RobotPlayer.this, minCards, oldOrderNo);
                } else {
                    byte[] cardsGreaterThan = CardUtils.getCardsGreaterThan(lastHistory.getCards(), getHandCards());
                    if (cardsGreaterThan != null && cardsGreaterThan.length > 0) {
                        table.playCards(RobotPlayer.this, cardsGreaterThan, oldOrderNo);
                    } else {
                        table.playNothing(RobotPlayer.this, oldOrderNo);
                    }
                }
            }
        }, delay, TimeUnit.SECONDS);

    }
}
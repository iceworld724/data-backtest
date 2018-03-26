package com.pengjia.data.backtest.core.deal.quit;

import com.pengjia.data.backtest.core.Position;

public class QuitCondition {

    private final QuitType quitType;
    private final float percent;

    public QuitCondition(QuitType quitType, float percent) {
        this.quitType = quitType;
        this.percent = percent;
    }

    public boolean match(Position position, float price) {
        switch (position.type) {
            case LONG:
                switch (quitType) {
                    case STOP_LOSS:
                        return (position.prices.price - price) / position.prices.price > percent;
                    case MOVING_STOP_LOSS:
                        return (position.prices.highestPrice - price) / position.prices.highestPrice > percent;
                    case TAKE_PROFIT:
                        return (price - position.prices.price) / position.prices.price > percent;
                }
            case SHORT:
                switch (quitType) {
                    case STOP_LOSS:
                        return (price - position.prices.price) / position.prices.price > percent;
                    case MOVING_STOP_LOSS:
                        return (price - position.prices.lowestPrice) / position.prices.lowestPrice > percent;
                    case TAKE_PROFIT:
                        return (position.prices.price - price) / position.prices.price > percent;
                }
            default:
                return true;
        }
    }
}

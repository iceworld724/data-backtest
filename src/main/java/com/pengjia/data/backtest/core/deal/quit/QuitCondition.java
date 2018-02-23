package com.pengjia.data.backtest.core.deal.quit;

import com.pengjia.data.backtest.core.Deal;

public class QuitCondition {

    private final QuitType quitType;
    private final float percent;

    public QuitCondition(QuitType quitType, float percent) {
        this.quitType = quitType;
        this.percent = percent;
    }
    
    public boolean match(Deal deal, float price){
        switch(deal.type){
            case LONG:
                switch(quitType) {
                    case STOP_LOSS:
                        return (deal.prices.price - price) / deal.prices.price > percent;
                    case MOVING_STOP_LOSS:
                        return (deal.prices.highestPrice - price) / deal.prices.highestPrice > percent;
                    case TAKE_PROFIT:
                        return (price - deal.prices.price) / deal.prices.price > percent;
                }
            case SHORT:
                switch(quitType) {
                    case STOP_LOSS:
                        return (price - deal.prices.price) / deal.prices.price > percent;
                    case MOVING_STOP_LOSS:
                        return (price - deal.prices.lowestPrice) / deal.prices.lowestPrice > percent;
                    case TAKE_PROFIT:
                        return (deal.prices.price - price) / deal.prices.price > percent;
                }
            default:
                return true;
        }
    }
}

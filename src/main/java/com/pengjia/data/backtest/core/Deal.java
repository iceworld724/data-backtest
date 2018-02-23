package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.deal.DealStatus;
import com.pengjia.data.backtest.core.deal.quit.QuitCondition;
import com.pengjia.data.backtest.core.trade.TradeType;
import java.util.ArrayList;
import java.util.Collection;

public class Deal {

    public static class Prices {

        public float highestPrice = Float.MIN_VALUE;
        public float lowestPrice = Float.MAX_VALUE;
        public float price;
    }

    public float profit;
    public float deposit;
    public float minDeposit;
    public String symbol;
    public TradeType type;
    public int num;
    public DealStatus status;
    public Prices prices = new Prices();
    public Collection<QuitCondition> quitConditions
            = new ArrayList<QuitCondition>();

    public float finish(float price) {
        profit = profit(this, price);
        deposit = 0;
        minDeposit = 0;
        num = 0;
        status = DealStatus.COMPLETE;
        setPrice(price);
        return marketValue(price);
    }

    public void setPrice(float price) {
        if (prices.highestPrice < price) {
            prices.highestPrice = price;
        }
        if (prices.lowestPrice > price) {
            prices.lowestPrice = price;
        }
    }

    public float marketValue(float price) {
        return deposit + profit(this, price);
    }

    public static float profit(Deal deal, float price) {
        switch (deal.type) {
            case LONG:
                return (price - deal.prices.price) * deal.num;
            case SHORT:
                return (deal.prices.price - price) * deal.num;
            default:
                return 0;
        }
    }
}

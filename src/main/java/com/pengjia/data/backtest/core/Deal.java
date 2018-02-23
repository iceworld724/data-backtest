package com.pengjia.data.backtest.core;

import java.util.ArrayList;
import java.util.Collection;

public class Deal {

    public static class Prices {

        public float highestPrice;
        public float lowestPrice;
        public float price;
    }

    public float profit;
    public float deposit;
    public float minDeposit;
    public String symbol;
    public DealType type;
    public int num;
    public DealStatus status;
    public Prices prices = new Prices();
    public Collection<QuitCondition> quitConditions
            = new ArrayList<QuitCondition>();

    public float finish(float price) {
        profit = profit(this, price);
        deposit = 0;
        minDeposit = 0;
        float cash = num * price;
        num = 0;
        status = DealStatus.COMPLETE;
        setPrice(price);
        return cash;
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

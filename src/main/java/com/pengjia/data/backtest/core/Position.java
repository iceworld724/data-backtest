package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.position.QuitCondition;
import com.pengjia.data.backtest.core.position.PositionType;
import java.util.ArrayList;
import java.util.Collection;

public class Position {

    public static class HistoryPrices {

        public float highestPrice = Float.MIN_VALUE;    // 最高价
        public float lowestPrice = Float.MAX_VALUE;    // 最低价
        public float price;    // 成本价

        public void setPrice(float price) {
            if (price > highestPrice) {
                highestPrice = price;
            }
            if (price < lowestPrice) {
                lowestPrice = price;
            }
            this.price = price;
        }
    }

    public String symbol;
    public PositionType type;
    public int num;
    public HistoryPrices prices = new HistoryPrices();
    public Collection<QuitCondition> quitConditions
            = new ArrayList<QuitCondition>();

    public float profit(Data data) {
        switch (type) {
            case LONG:
                return num * (data.latestPrice(symbol) - prices.price);
            case SHORT:
                return num * (prices.price - data.latestPrice(symbol));
            default:
                return 0;
        }
    }

    public float value(Data data) {
        return num * prices.price + profit(data);
    }
}

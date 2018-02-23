package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.trade.TradeType;
import com.pengjia.data.backtest.core.data.DataPointer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Account {

    private float cash;
    private List<Deal> unCompleteDeals = new ArrayList<Deal>();
    private List<Deal> completeDeals = new ArrayList<Deal>();

    public boolean isShortOn(DataPointer pointer) {
        for (Deal deal : unCompleteDeals) {
            if (deal.symbol.equals(pointer.getSymbol())
                    && deal.type.equals(TradeType.SHORT)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLongOn(DataPointer pointer) {
        for (Deal deal : unCompleteDeals) {
            if (deal.symbol.equals(pointer.getSymbol())
                    && deal.type.equals(TradeType.LONG)) {
                return true;
            }
        }
        return false;
    }

    public synchronized float marketValue(Map<String, Float> prices) {
        float sum = cash;
        for (Deal deal : unCompleteDeals) {
            sum += deal.marketValue(prices.get(deal.symbol));
        }
        return sum;
    }
}

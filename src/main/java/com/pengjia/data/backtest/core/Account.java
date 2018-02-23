package com.pengjia.data.backtest.core;

import java.util.List;
import java.util.Map;

public class Account {

    private float cash;
    private List<Deal> unCompleteDeals;
    private List<Deal> completeDeals;

    public boolean isShortOn(DataPointer pointer) {
        for (Deal deal : unCompleteDeals) {
            if (deal.symbol.equals(pointer.getSymbol())
                    && deal.type.equals(DealType.SHORT)) {
                return true;
            }
        }
        return false;
    }

    public boolean isLongOn(DataPointer pointer) {
        for (Deal deal : unCompleteDeals) {
            if (deal.symbol.equals(pointer.getSymbol())
                    && deal.type.equals(DealType.LONG)) {
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

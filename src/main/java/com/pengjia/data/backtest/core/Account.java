package com.pengjia.data.backtest.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Account {

    private float cash;
    private Map<String, List<Deal>> unCompleteDeals
            = new ConcurrentHashMap<String, List<Deal>>();
    private Map<String, List<Deal>> completeDeals
            = new ConcurrentHashMap<String, List<Deal>>();

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public void addDeal(Deal deal) {
        List<Deal> deals = unCompleteDeals.get(deal.symbol);
        if (deals == null) {
            deals = new ArrayList<Deal>();
        }
        deals.add(deal);
        unCompleteDeals.put(deal.symbol, deals);
    }

    public synchronized float marketValue(Map<String, Float> prices) {
        float sum = cash;
        for (List<Deal> deals : unCompleteDeals.values()) {
            for (Deal deal : deals) {
                sum += deal.marketValue(prices.get(deal.symbol));
            }
        }
        return sum;
    }
}

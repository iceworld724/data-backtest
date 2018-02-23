package com.pengjia.data.backtest.core;

import java.util.ArrayList;
import java.util.Collection;

public class Deal {

    public static class Prices {

        public float highestPrice;
        public float lowestPrice;
        public float price;
    }

    public String symbol;
    public DealType type;
    public int num;
    public DealStatus status;
    public Prices prices = new Prices();
    public Collection<QuitCondition> quitConditions
            = new ArrayList<QuitCondition>();
}

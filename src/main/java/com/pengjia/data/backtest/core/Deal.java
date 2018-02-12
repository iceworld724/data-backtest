package com.pengjia.data.backtest.core;

import java.util.Collection;

public class Deal {

    private DealType type;
    private float price;
    private int num;
    private Collection<QuitCondition> quitConditions;
    private DealStatus status;
}

package com.pengjia.data.backtest.core.trade;

import com.pengjia.data.backtest.core.Code;

public class Order {

    public Code symbol;
    public TradeType type;
    public float price;
    public float num;

    public float value() {
        return price * num;
    }
}

package com.pengjia.data.backtest.core.trade;

public class Order {

    public String symbol;
    public TradeType type;
    public float price;
    public int num;
    
    public float value(){
        return price * num;
    }
}

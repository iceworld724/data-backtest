package com.pengjia.data.backtest.core;

import java.util.Collection;

public class Deal {

    private String symbol;
    private DealType type;
    private float price;
    private int num;
    private Collection<QuitCondition> quitConditions;
    private DealStatus status;

    public DealType getType() {
        return type;
    }

    public void setType(DealType type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Collection<QuitCondition> getQuitConditions() {
        return quitConditions;
    }

    public void setQuitConditions(Collection<QuitCondition> quitConditions) {
        this.quitConditions = quitConditions;
    }

    public DealStatus getStatus() {
        return status;
    }

    public void setStatus(DealStatus status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

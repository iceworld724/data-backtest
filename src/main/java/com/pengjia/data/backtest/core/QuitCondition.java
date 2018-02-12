package com.pengjia.data.backtest.core;

public class QuitCondition {

    private final QuitType quitType;
    private final float quitPercent;

    public QuitCondition(QuitType quitType, float quitPercent) {
        this.quitType = quitType;
        this.quitPercent = quitPercent;
    }
    
    public boolean match(Deal deal){
        throw new UnsupportedOperationException("TODO");
    }
}

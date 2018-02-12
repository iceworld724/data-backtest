package com.pengjia.data.backtest.core;

public class DataPointer {

    private int index = 0;
    private final Data data;

    public DataPointer(Data data) {
        this.data = data;
    }
    
    public DataUnit next(int step) {
        index += step;
        return data.getDataUnits().get(index);
    }
    
    public DataUnit back(int step) {
        index -= step;
        return data.getDataUnits().get(index);
    }
    
    public DataUnit get(){
        return data.getDataUnits().get(index);
    }
    
    public String getSymbol(){
        return data.getSymbol();
    }
}

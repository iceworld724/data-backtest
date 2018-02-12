package com.pengjia.data.backtest.core;

import java.util.List;

public class DataUnitPointer {

    private int index = 0;
    private final List<DataUnit> dataUnits;

    public DataUnitPointer(List<DataUnit> dataUnits) {
        this.dataUnits = dataUnits;
    }
    
    public DataUnit next(int step) {
        index += step;
        return dataUnits.get(index);
    }
    
    public DataUnit back(int step) {
        index -= step;
        return dataUnits.get(index);
    }
    
    public DataUnit get(){
        return dataUnits.get(index);
    }
}

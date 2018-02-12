package com.pengjia.data.backtest.core;

import java.util.List;

public interface Data {

    float getSignal(Signal signal);
    
    List<DataUnit> getDataSeries();
    
    String getSymbol();
}

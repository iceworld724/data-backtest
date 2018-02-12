package com.pengjia.data.backtest.core;

import java.util.List;

public class Data {
    
    private List<DataUnit> dataUnits;
    private String symbol;

    public List<DataUnit> getDataUnits() {
        return dataUnits;
    }

    public void setDataUnits(List<DataUnit> dataUnits) {
        this.dataUnits = dataUnits;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

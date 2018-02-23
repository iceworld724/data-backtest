package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnit;
import java.util.ArrayList;
import java.util.List;

public class Data {
    
    private List<DataUnit> dataUnits = new ArrayList<DataUnit>();
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

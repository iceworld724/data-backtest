package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnit;
import com.pengjia.data.backtest.core.data.DataUnitComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {

    private List<DataUnit> dataUnits = new ArrayList<DataUnit>();
    private String symbol;

    public Data() {
    }

    public Data(String symbol) {
        this.symbol = symbol;
    }

    public void addDataUnit(DataUnit dataUnit) {
        dataUnits.add(dataUnit);
    }

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

    public void sort() {
        Collections.sort(dataUnits, new DataUnitComparator());
    }
}

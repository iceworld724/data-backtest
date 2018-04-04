package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnit;
import com.pengjia.data.backtest.core.data.DataUnitComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;

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

    public Data subData(int begin, int end) {
        Data newData = new Data(symbol);
        newData.setDataUnits(dataUnits.subList(begin, end));
        return newData;
    }

    public Data subData(int end) {
        return subData(0, end + 1);
    }
 
    public DataUnit latestUnit(){
        return dataUnits.get(dataUnits.size() - 1);
    }

    public float latestPrice() {
        return latestUnit().getClose();
    }

    public DateTime latestTime() {
        return latestUnit().getBeginTime();
    }
}

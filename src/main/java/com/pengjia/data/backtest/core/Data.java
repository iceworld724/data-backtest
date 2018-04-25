package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnits;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;

public class Data {

    private List<DataUnits> dataSeries = new ArrayList<>();

    public Data() {
    }

    public void addDataUnits(DataUnits dataUnits) {
        dataSeries.add(dataUnits);
    }

    public List<DataUnits> getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(List<DataUnits> dataSeries) {
        this.dataSeries = dataSeries;
    }

    public void sort() {
        Collections.sort(dataSeries,
                (DataUnits o1, DataUnits o2)
                -> o1.getBeginTime().compareTo(o2.getBeginTime())
        );
    }

    public Data subData(int begin, int end) {
        Data newData = new Data();
        newData.setDataSeries(dataSeries.subList(begin, end));
        return newData;
    }

    public Data subData(int end) {
        return subData(0, end + 1);
    }

    public DataUnits latestUnits() {
        return dataSeries.get(dataSeries.size() - 1);
    }

    public float latestPrice(String symbol) {
        return latestUnits().getDataUnit(symbol).getClose();
    }

    public DateTime latestTime() {
        return latestUnits().getBeginTime();
    }

    public int size() {
        return dataSeries.size();
    }
}

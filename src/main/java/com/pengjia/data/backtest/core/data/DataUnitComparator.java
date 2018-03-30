package com.pengjia.data.backtest.core.data;

import java.util.Comparator;

public class DataUnitComparator implements Comparator<DataUnit> {

    @Override
    public int compare(DataUnit o1, DataUnit o2) {
        return o1.getBeginTime().compareTo(o2.getBeginTime());
    }
}

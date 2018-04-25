package com.pengjia.data.backtest.core.data;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;

public class DataUnits {

    private DateTime beginTime;
    private DateTime endTime;
    private Map<String, DataUnit> dataUnits = new HashMap<String, DataUnit>();

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public DateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(DateTime beginTime) {
        this.beginTime = beginTime;
    }

    public DataUnit getDataUnit(String symbol) {
        return dataUnits.get(symbol);
    }

    public void setDataUnit(String symbol, DataUnit unit) {
        dataUnits.put(symbol, unit);
    }

    public Map<String, DataUnit> getDataUnits() {
        return dataUnits;
    }

    public int size() {
        return dataUnits.size();
    }
}

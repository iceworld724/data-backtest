package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.joda.time.DateTime;

public class Data {

    private TreeMap<DateTime, Map<String, DataUnit>> dataSeries = new TreeMap<>();

    public Data() {
    }

    public void addDataUnit(DateTime time, String symbol, DataUnit unit) {
        if (dataSeries.containsKey(time)) {
            dataSeries.get(time).put(symbol, unit);
        } else {
            Map<String, DataUnit> map = new HashMap<>();
            map.put(symbol, unit);
            dataSeries.put(time, map);
        }
    }

    public Map<DateTime, Map<String, DataUnit>> getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(TreeMap<DateTime, Map<String, DataUnit>> dataSeries) {
        this.dataSeries = dataSeries;
    }

    public Data subData(DateTime begin, DateTime end) {
        Data newData = new Data();
        dataSeries.entrySet().stream().filter(
                e -> (begin == null || e.getKey().isAfter(begin) || e.getKey().equals(begin))
                && (end == null || e.getKey().isBefore(end) || e.getKey().equals(end)))
                .forEach(e -> {
                    newData.dataSeries.put(e.getKey(), e.getValue());
                });
        return newData;
    }

    public Data subData(DateTime end) {
        return subData(null, end);
    }

    public Map<String, DataUnit> latestUnits() {
        return dataSeries.get(dataSeries.lastKey());
    }

    public float latestPrice(String symbol) {
        return latestUnits().get(symbol).getClose();
    }

    public DateTime firstTime() {
        return dataSeries.firstKey();
    }

    public DateTime latestTime() {
        return dataSeries.lastKey();
    }

    public int size() {
        return dataSeries.size();
    }

    public void merge(Data data) {
        for (Entry<DateTime, Map<String, DataUnit>> entry : data.dataSeries.entrySet()) {
            for (Entry<String, DataUnit> entry2 : entry.getValue().entrySet()) {
                addDataUnit(entry.getKey(), entry2.getKey(), entry2.getValue());
            }
        }
    }
}

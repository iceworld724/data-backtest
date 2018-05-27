package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import scala.Tuple2;
import scala.Tuple3;

public class Data {

    private NavigableMap<DateTime, Map<Code, DataUnit>> dataSeries
            = new TreeMap<>((dt1, dt2) -> dt1.compareTo(dt2));

    public Data() {
    }

    public void addDataUnit(DateTime time, Code symbol, DataUnit unit) {
        if (dataSeries.containsKey(time)) {
            dataSeries.get(time).put(symbol, unit);
        } else {
            Map<Code, DataUnit> map = new HashMap<>();
            map.put(symbol, unit);
            dataSeries.put(time, map);
        }
    }

    public Map<DateTime, Map<Code, DataUnit>> getDataSeries() {
        return dataSeries;
    }

    public void setDataSeries(TreeMap<DateTime, Map<Code, DataUnit>> dataSeries) {
        this.dataSeries = dataSeries;
    }

    public Data subData(DateTime begin, DateTime end) {
        if (begin == null) {
            begin = dataSeries.firstKey();
        }
        if (end == null) {
            end = dataSeries.lastKey();
        }

        Data newData = new Data();
        newData.dataSeries = dataSeries.subMap(begin, true, end, true);
        return newData;
    }

    public Data subData(DateTime end) {
        return subData(null, end);
    }

    public Map<Code, DataUnit> latestUnits() {
        return dataSeries.lastEntry().getValue();
    }

    public float latestPrice(Code symbol) {
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
        for (Entry<DateTime, Map<Code, DataUnit>> entry : data.dataSeries.entrySet()) {
            for (Entry<Code, DataUnit> entry2 : entry.getValue().entrySet()) {
                addDataUnit(entry.getKey(), entry2.getKey(), entry2.getValue());
            }
        }
    }

    public NavigableMap<DateTime, Map<String, Tuple2<Code, DataUnit>>> mainSeries(){
        NavigableMap<DateTime, Map<String, Tuple2<Code, DataUnit>>> mainSeries
                = new TreeMap<>((dt1, dt2) -> dt1.compareTo(dt2));
        DateTime prevTime = null;
        for (Entry<DateTime, Map<Code, DataUnit>> entry : dataSeries.entrySet()) {
            DateTime dateTime = entry.getKey();
            Map<Code, DataUnit> units = entry.getValue();
            for (Entry<Code, DataUnit> entry2 : units.entrySet()){
                Code code = entry2.getKey();
                DataUnit unit = entry2.getValue();
                if (prevTime == null || mainSeries.get(dateTime) == null) {
                    Map<String, Tuple2<Code, DataUnit>> newMap = new HashMap<>();
                    newMap.put(code.getSymbol(), new Tuple2<>(code, unit));
                    mainSeries.put(dateTime, newMap);
                } else if(!mainSeries.get(dateTime).containsKey(code.getSymbol())) {
                    Map<String, Tuple2<Code, DataUnit>> oldMap = mainSeries.get(dateTime);
                    oldMap.put(code.getSymbol(), new Tuple2<>(code, unit));
                } else {
                    Map<String, Tuple2<Code, DataUnit>> oldMap = mainSeries.get(dateTime);
                    Tuple2<Code, DataUnit> oldMain = oldMap.get(code.getSymbol());
                    if(unit.getOi() > oldMain._2.getOi() && unit.getVol() > oldMain._2.getVol() && code.getMonth() > oldMain._1.getMonth()) {
                        oldMap.put(code.getSymbol(), new Tuple2<>(code, unit));
                    }
                }
            }
            prevTime = dateTime;
        }
        return mainSeries;
    }
}

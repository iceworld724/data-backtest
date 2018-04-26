package com.pengjia.data.backtest.core.simulator;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class ReportCollector {

    private ConcurrentSkipListMap<DateTime, Float> dateValues
            = new ConcurrentSkipListMap<>((d1, d2) -> d1.compareTo(d2));
    private static double DAYS_OF_YEAR = 365d;

    public void collect(DateTime date, float value) {
        dateValues.put(date, value);
    }

    public Report makeReport() {
        Report report = new Report();
        report.CAGR = calCAGR();
        return report;
    }

    public float calCAGR() {
        Entry<DateTime, Float> first = dateValues.firstEntry();
        Entry<DateTime, Float> last = dateValues.lastEntry();
        int days = Days.daysBetween(first.getKey(), last.getKey()).getDays();
        return (float) (Math.pow(last.getValue() / first.getValue(), DAYS_OF_YEAR / days) - 1d);
    }
}

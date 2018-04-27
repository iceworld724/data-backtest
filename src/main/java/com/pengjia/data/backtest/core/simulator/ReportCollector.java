package com.pengjia.data.backtest.core.simulator;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class ReportCollector {

    private ConcurrentSkipListMap<DateTime, Float> dateValues
            = new ConcurrentSkipListMap<>((d1, d2) -> d1.compareTo(d2));
    private static double DAYS_OF_YEAR = 365d;
    private static Gson GSON = new Gson();

    public void collect(DateTime date, float value) {
        dateValues.put(date, value);
    }

    public Report makeReport() {
        Report report = new Report();
        report.CAGR = calCAGR();
        report.MAX_DRAW_BACK = calMaxDrawBacks();
        report.generateTime = System.currentTimeMillis() - report.createTime;
        System.out.println(GSON.toJson(report));
        return report;
    }

    public float calCAGR() {
        Entry<DateTime, Float> first = dateValues.firstEntry();
        Entry<DateTime, Float> last = dateValues.lastEntry();
        int days = Days.daysBetween(first.getKey(), last.getKey()).getDays();
        return (float) (Math.pow(last.getValue() / first.getValue(), DAYS_OF_YEAR / days) - 1d);
    }

    public float calMaxDrawBacks() {
        float max = Float.MIN_VALUE;
        List<Entry<DateTime, Float>> entries = new ArrayList<>(dateValues.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            Entry<DateTime, Float> before = entries.get(i);
            for (int j = i + 1; j < entries.size(); j++) {
                Entry<DateTime, Float> after = entries.get(j);
                float draw = (after.getValue() - before.getValue()) / before.getValue();
                if (draw < max) {
                    max = draw;
                }
            }
        }
        return max;
    }
}

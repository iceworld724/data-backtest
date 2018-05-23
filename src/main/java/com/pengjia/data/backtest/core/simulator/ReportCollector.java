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
        calCAGR(report);
        calMaxDrawBacks(report);
        calSharpe(report);
        report.generateTime = System.currentTimeMillis() - report.createTime;
        System.out.println(GSON.toJson(report));
        return report;
    }

    public void calCAGR(Report report) {
        Entry<DateTime, Float> first = dateValues.firstEntry();
        Entry<DateTime, Float> last = dateValues.lastEntry();
        int days = Days.daysBetween(first.getKey(), last.getKey()).getDays();
        report.CAGR = (float) (Math.pow(last.getValue() / first.getValue(), DAYS_OF_YEAR / days) - 1d);
    }

    public void calMaxDrawBacks(Report report) {
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
        report.MAX_DRAW_BACK = max;
    }

    public void calSharpe(Report report) {
        List<Entry<DateTime, Float>> entries = new ArrayList<>(dateValues.entrySet());
        List<Float> profits = new ArrayList<>(entries.size() - 1);
        for (int i = 0; i < entries.size() - 1; i++) {
            profits.add((entries.get(i + 1).getValue() - entries.get(i).getValue()) / entries.get(i).getValue());
        }
        float sum = profits.stream().reduce((f1, f2) -> f1 + f2).get();
        report.DAY_MEAN = sum / (float) profits.size();
        report.DAY_SD = (float) (Math.sqrt(profits.stream().mapToDouble(profit -> Math.pow(profit - report.DAY_MEAN, 2)).sum() / ((double) (profits.size() - 1))));
        report.DAY_SHARPE = (report.DAY_MEAN - (0.04f / 365f)) / report.DAY_SD;
    }
}

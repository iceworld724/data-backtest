package com.pengjia.data.backtest.core.simulator;

public class Report {

    public float CAGR;
    public float MAX_DRAW_BACK;
    public float DAY_SHARPE;
    public float DAY_SD;
    public float DAY_MEAN;
    public long createTime = System.currentTimeMillis();
    public long generateTime = 0L;
}

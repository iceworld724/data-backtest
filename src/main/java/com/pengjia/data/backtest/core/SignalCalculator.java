package com.pengjia.data.backtest.core;

import java.util.List;

public interface SignalCalculator {

    float calculate(List<DataUnit> dataSeries);
}

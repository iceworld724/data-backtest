package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.data.DataUnit;
import java.util.List;

public interface SignalCalculator {

    float calculate(List<DataUnit> data);
}

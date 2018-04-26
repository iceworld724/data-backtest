package com.pengjia.data.backtest.core.simulator;

import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.strategy.Strategy;

public interface Simulator {

    Report simulate(Data data, Strategy strategy);
}

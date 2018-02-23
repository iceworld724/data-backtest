package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.trade.Order;

public interface Trader {

    void trade(Account account, Order order);
}

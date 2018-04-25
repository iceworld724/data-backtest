package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.trade.Order;
import java.util.List;

public interface Strategy {

    List<Order> makeOrder(Data data, Account account);
}

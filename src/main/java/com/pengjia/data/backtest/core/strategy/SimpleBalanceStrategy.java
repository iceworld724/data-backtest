package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.trade.Order;
import java.util.List;

public class SimpleBalanceStrategy implements Strategy {

    public static final float RATIO = 0.6f;
    public static final float VAR = 0.1f;

    @Override
    public List<Order> makeOrder(List<Data> datas, Account account) {
        return null;
    }
}

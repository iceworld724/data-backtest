package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.trade.Order;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class SimpleBalanceStrategy implements Strategy {

    private final float cashRatio;
    private final DateTime interval;
    private DateTime lastAction;

    public SimpleBalanceStrategy(float cashRatio, DateTime interval) {
        this.cashRatio = cashRatio;
        this.interval = interval;
    }

    @Override
    public List<Order> makeOrder(List<Data> datas, Account account) {
        float value = account.value(datas);
        float dataValue = value * cashRatio / datas.size();
        List<Order> orders = new ArrayList<Order>();
        for (Data data : datas) {
            Order order = new Order();
            
        }
        return null;
    }
}

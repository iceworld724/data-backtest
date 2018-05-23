package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.trade.Constant;
import com.pengjia.data.backtest.core.trade.Order;
import com.pengjia.data.backtest.core.trade.TradeType;

import java.util.ArrayList;
import java.util.List;

public class SimpleHoldStrategy implements Strategy {

    @Override
    public List<Order> makeOrder(Data data, Account account) {
        List<Order> orders = new ArrayList<Order>();
        if (account.getCash() > 0) {
            Order order = new Order();
            String symbol = data.getDataSeries().get(0).keySet().iterator().next();
            float latestPrice = data.latestPrice(symbol);
            float fee = Math.max(order.num * order.price * Constant.FEE_RATIO, Constant.MIN_FEE);
            order.num = (int) Math.min(Math.floor(account.getCash() / latestPrice / (1 + Constant.FEE_RATIO)),
                    Math.floor((account.getCash() - Constant.MIN_FEE) / latestPrice));
            if (order.num % 100 != 0) {
                order.num -= order.num % 100;
            }
            order.price = latestPrice;
            order.symbol = symbol;
            order.type = TradeType.LONG;
            if (order.num > 0) {
                orders.add(order);
            }
        }
        return orders;
    }
}

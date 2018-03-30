package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.trade.Order;
import com.pengjia.data.backtest.core.trade.TradeType;
import java.util.ArrayList;
import java.util.List;

public class SimpleHoldStrategy implements Strategy {

    @Override
    public List<Order> makeOrder(List<Data> datas, Account account) {
        List<Order> orders = new ArrayList<Order>();
        if (account.getCash() > 0) {
            Order order = new Order();
            float latestPrice = datas.get(0).getDataUnits().get(
                    datas.get(0).getDataUnits().size() - 1).getClose();
            order.num = (int) Math.floor(account.getCash() / latestPrice);
            order.price = latestPrice;
            order.symbol = datas.get(0).getSymbol();
            order.type = TradeType.LONG;
            orders.add(order);
        }
        return orders;
    }
}

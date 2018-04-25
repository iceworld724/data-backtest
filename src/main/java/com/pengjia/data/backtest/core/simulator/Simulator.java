package com.pengjia.data.backtest.core.simulator;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.Trader;
import com.pengjia.data.backtest.core.data.CSVDataLoader;
import com.pengjia.data.backtest.core.data.DataLoader;
import com.pengjia.data.backtest.core.strategy.SimpleBalanceStrategy;
import com.pengjia.data.backtest.core.strategy.Strategy;
import com.pengjia.data.backtest.core.trade.Order;
import com.pengjia.data.backtest.core.trade.SimpleTrader;
import java.util.List;

public class Simulator {

    public static void main(String[] args) throws Exception {
        Account account = new Account();
        account.setCash(10000000f);
        Strategy strategy = new SimpleBalanceStrategy(0.4f, 0.05f, 86400);

        Trader trader = new SimpleTrader();

        DataLoader loader = new CSVDataLoader(args[0], args[1]);

        Data data = loader.load();
        for (int i = 0; i < data.size(); i++) {
            Data subData = data.subData(i);
            List<Order> orders = strategy.makeOrder(subData, account);
            for (Order order : orders) {
                trader.trade(account, order);
            }
            System.out.println(account.value(subData));
        }
    }
}

package com.pengjia.data.backtest.core.simulator;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.Trader;
import com.pengjia.data.backtest.core.data.CSVDataLoader;
import com.pengjia.data.backtest.core.data.DataLoader;
import com.pengjia.data.backtest.core.strategy.SimpleHoldStrategy;
import com.pengjia.data.backtest.core.strategy.Strategy;
import com.pengjia.data.backtest.core.trade.Order;
import com.pengjia.data.backtest.core.trade.SimpleTrader;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public static void main(String[] args) throws Exception {
        List<Data> datas = new ArrayList<Data>();

        Account account = new Account();
        account.setCash(10000000f);
        System.out.println(account.value(datas));

        Strategy strategy = new SimpleHoldStrategy();

        Trader trader = new SimpleTrader();

        DataLoader loader = new CSVDataLoader(args[0], args[1]);

        Data data = loader.load();
        for (int i = 0; i < data.getDataUnits().size(); i++) {
            Data subData = data.subData(i);
            datas.clear();
            datas.add(subData);
            List<Order> orders = strategy.makeOrder(datas, account);
            for (Order order : orders) {
                trader.trade(account, order);
            }
            System.out.println(account.value(datas));
        }
    }
}

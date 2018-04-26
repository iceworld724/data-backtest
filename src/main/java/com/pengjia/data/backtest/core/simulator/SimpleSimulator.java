package com.pengjia.data.backtest.core.simulator;

import com.google.gson.Gson;
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

public class SimpleSimulator implements Simulator {

    private static Gson GSON = new Gson();

    public static void main(String[] args) throws Exception {

        Strategy strategy1 = new SimpleBalanceStrategy(0.5f, 0.2f, 86400);
        Strategy strategy2 = new SimpleBalanceStrategy(0.5f, 0.1f, 86400);
        Strategy strategy3 = new SimpleBalanceStrategy(0.5f, 0.05f, 86400);

        DataLoader loader = new CSVDataLoader(args[0], args[1]);

        Simulator simulator = new SimpleSimulator();

        System.out.println(GSON.toJson(simulator.simulate(loader.load(), strategy1)));
        System.out.println(GSON.toJson(simulator.simulate(loader.load(), strategy2)));
        System.out.println(GSON.toJson(simulator.simulate(loader.load(), strategy3)));
    }

    @Override
    public Report simulate(Data data, Strategy strategy) {
        Account account = new Account();
        account.setCash(10000000f);

        ReportCollector collector = new ReportCollector();

        Trader trader = new SimpleTrader();
        for (int i = 0; i < data.size(); i++) {
            Data subData = data.subData(i);
            List<Order> orders = strategy.makeOrder(subData, account);
            if (orders != null) {
                for (Order order : orders) {
                    trader.trade(account, order);
                }
                //System.out.println(GSON.toJson(account));
            }
            collector.collect(subData.latestTime(), account.value(subData));
        }

        return collector.makeReport();
    }
}

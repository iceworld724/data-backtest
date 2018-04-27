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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleSimulator implements Simulator {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        DataLoader loader = new CSVDataLoader(args[0], args[1]);

        Data data = loader.load();

        for (float bias = 0.05f; bias < 0.5f; bias = bias + 0.0001f) {

            Strategy strategy = new SimpleBalanceStrategy(0.5f, bias, 86400);

            Simulator simulator = new SimpleSimulator();

            executor.submit(() -> simulator.simulate(data, strategy));
        }

        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.MINUTES);

        executor.shutdownNow();
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

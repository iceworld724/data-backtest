package com.pengjia.data.backtest.core.simulator;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Code;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.Trader;
import com.pengjia.data.backtest.core.data.DataLoader;
import com.pengjia.data.backtest.core.data.DataUnit;
import com.pengjia.data.backtest.core.data.WindDirCSVDataLoader;
import com.pengjia.data.backtest.core.strategy.SimpleBalanceStrategy;
import com.pengjia.data.backtest.core.strategy.SimpleHoldStrategy;
import com.pengjia.data.backtest.core.strategy.Strategy;
import com.pengjia.data.backtest.core.trade.Order;
import com.pengjia.data.backtest.core.trade.SimpleTrader;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;
import scala.Tuple2;

public class SimpleSimulator implements Simulator {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        DataLoader loader = new WindDirCSVDataLoader(new File(args[0]));

        Data data = loader.load();

        NavigableMap<DateTime, Map<String, Tuple2<Code, DataUnit>>> mainSeries =  data.mainSeries();

        for (float bias = 0.05f; bias < 0.5f; bias = bias + 0.01f) {

            Strategy strategy = new SimpleBalanceStrategy(0.5f, bias, 86400);

            //Strategy strategy = new SimpleHoldStrategy();
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
        for (DateTime time : data.getDataSeries().keySet()) {
            Data subData = data.subData(time);
            List<Order> orders = strategy.makeOrder(subData, account);
            if (orders != null) {
                for (Order order : orders) {
                    trader.trade(account, order);
                }
            }
            collector.collect(subData.latestTime(), account.value(subData));
        }

        return collector.makeReport();
    }
}

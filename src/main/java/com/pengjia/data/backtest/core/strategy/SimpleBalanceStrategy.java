package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.Position;
import com.pengjia.data.backtest.core.position.PositionType;
import com.pengjia.data.backtest.core.trade.Order;
import com.pengjia.data.backtest.core.trade.TradeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joda.time.DateTime;

public class SimpleBalanceStrategy implements Strategy {

    private final float cashRatio;
    private final int interval;
    private final float maxBias;
    private DateTime lastTime;

    public SimpleBalanceStrategy(float cashRatio, float bias, int interval) {
        this.cashRatio = cashRatio;
        this.interval = interval;
        this.maxBias = bias;
    }

    @Override
    public List<Order> makeOrder(Data data, Account account) {
        DateTime time = data.latestTime();
        if (lastTime == null || lastTime.plusSeconds(interval).isBefore(time)) {
            float baseValue = account.value(data) * (1 - cashRatio) / data.latestUnits().size();
            List<Order> orders = new ArrayList<Order>();
            Collection<String> symbols = data.latestUnits().getDataUnits().keySet();
            for (String symbol : symbols) {
                List<Position> positionList = account.getPositions(symbol, PositionType.LONG);
                if (positionList == null || positionList.isEmpty()) {
                    Order order = new Order();
                    order.num = (int) (baseValue / data.latestPrice(symbol));
                    order.price = data.latestPrice(symbol);
                    order.symbol = symbol;
                    order.type = TradeType.LONG;
                    orders.add(order);
                    continue;
                }
                Position position = positionList.get(0);
                float value = position.value(data);
                float bias = (value - baseValue) / baseValue;
                if (bias < -maxBias) {
                    Order order = new Order();
                    order.num = (int) ((baseValue - value) / data.latestPrice(symbol));
                    order.price = data.latestPrice(symbol);
                    order.symbol = symbol;
                    order.type = TradeType.ADD_LONG;
                    orders.add(order);
                } else if (bias > maxBias) {
                    Order order = new Order();
                    order.num = (int) ((value - baseValue) / data.latestPrice(symbol));
                    order.price = data.latestPrice(symbol);
                    order.symbol = symbol;
                    order.type = TradeType.REDUCE_LONG;
                    orders.add(order);
                }
            }

            return orders;
        }
        return null;
    }
}

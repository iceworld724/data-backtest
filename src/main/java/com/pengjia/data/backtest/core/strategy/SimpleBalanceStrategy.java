package com.pengjia.data.backtest.core.strategy;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Data;
import com.pengjia.data.backtest.core.Position;
import com.pengjia.data.backtest.core.position.PositionType;
import com.pengjia.data.backtest.core.trade.Constant;
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
                    order.price = data.latestPrice(symbol);
                    order.num = (int) Math.min(Math.floor(baseValue / order.price / (1 + Constant.FEE_RATIO)),
                            Math.floor((baseValue - Constant.MIN_FEE) / order.price));
                    if (order.num % 100 != 0) {
                        order.num -= order.num % 100;
                    }
                    order.symbol = symbol;
                    order.type = TradeType.LONG;
                    if (order.num > 0) {
                        orders.add(order);
                    }
                    lastTime = data.latestTime();
                    continue;
                }
                Position position = positionList.get(0);
                float value = position.value(data);
                float bias = (value - baseValue) / baseValue;
                if (bias < -maxBias) {
                    Order order = new Order();
                    order.price = data.latestPrice(symbol);
                    order.num = (int) Math.min(Math.floor((baseValue - value) / 2f / order.price / (1 + Constant.FEE_RATIO)),
                            Math.floor(((baseValue - value) / 2f - Constant.MIN_FEE) / order.price));
                    if (order.num % 100 != 0) {
                        order.num -= order.num % 100;
                    }
                    order.symbol = symbol;
                    order.type = TradeType.ADD_LONG;
                    if (order.num > 0) {
                        orders.add(order);
                    }
                    lastTime = data.latestTime();
                    continue;
                } else if (bias > maxBias) {
                    Order order = new Order();
                    order.num = (int) ((value - baseValue) / data.latestPrice(symbol));
                    order.price = data.latestPrice(symbol);
                    order.num = (int) Math.min(Math.floor((value - baseValue) / 2f / order.price / (1 + Constant.FEE_RATIO)),
                            Math.floor(((value - baseValue) / 2f - Constant.MIN_FEE) / order.price));
                    if (order.num % 100 != 0) {
                        order.num -= order.num % 100;
                    }
                    order.symbol = symbol;
                    order.type = TradeType.REDUCE_LONG;
                    if (order.num > 0) {
                        orders.add(order);
                    }
                    lastTime = data.latestTime();
                    continue;
                }
            }
            return orders;
        }
        lastTime = data.latestTime();
        return null;
    }
}

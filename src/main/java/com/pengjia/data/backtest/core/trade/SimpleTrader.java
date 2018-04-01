package com.pengjia.data.backtest.core.trade;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Position;
import com.pengjia.data.backtest.core.Trader;
import com.pengjia.data.backtest.core.position.PositionType;

public class SimpleTrader implements Trader {

    @Override
    public void trade(Account account, Order order) {
        if (validate(account, order)) {
            innerTrade(account, order);
        }
    }

    private boolean validate(Account account, Order order) {
        return account.getCash() > order.value();
    }

    private void innerTrade(Account account, Order order) {
        synchronized (account) {
            Position position = new Position();
            switch (order.type) {
                case LONG:
                    account.setCash(account.getCash() - order.value());
                    position.num = order.num;
                    position.prices.setPrice(order.price);
                    position.symbol = order.symbol;
                    position.type = PositionType.LONG;
                    account.addPosition(position);
                    return;
                case SHORT:
                    account.setCash(account.getCash() + order.value());
                    position.num = order.num;
                    position.prices.setPrice(order.price);
                    position.symbol = order.symbol;
                    position.type = PositionType.SHORT;
                    account.addPosition(position);
                    return;
                case STOP_LONG:
                case STOP_SHORT:
                    return;
            }
        }
    }
}

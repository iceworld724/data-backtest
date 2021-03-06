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
        Position position = new Position();
        float fee = Math.max(order.num * order.price * Constant.FEE_RATIO, Constant.MIN_FEE);
        switch (order.type) {
            case LONG:
                if (account.getCash() > order.value() + fee) {
                    account.setCash(account.getCash() - order.value() - fee);
                    position.num = order.num;
                    position.prices.setPrice(order.price);
                    position.symbol = order.symbol;
                    position.type = PositionType.LONG;
                    account.addPosition(position);
                }
                return;
            case ADD_LONG:
                position = account.getPositions(order.symbol, PositionType.LONG).get(0);
                if (account.getCash() > order.value() + fee) {
                    account.setCash(account.getCash() - order.value() - fee);
                    position.prices.setPrice((order.price * order.num
                            + position.prices.price * position.num)
                            / (order.num + position.num));
                    position.num += order.num;
                    position.symbol = order.symbol;
                    position.type = PositionType.LONG;
                }
                return;
            case REDUCE_LONG:
                position = account.getPositions(order.symbol, PositionType.LONG).get(0);
                if (account.getPositions(order.symbol, PositionType.LONG).get(0).num > order.num) {
                    account.setCash(account.getCash() + order.value());
                    position.prices.setPrice((position.prices.price * position.num
                            - order.price * order.num)
                            / (position.num - order.num));
                    position.num -= order.num;
                    position.symbol = order.symbol;
                    position.type = PositionType.LONG;
                }
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

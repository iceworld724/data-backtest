package com.pengjia.data.backtest.core.trade;

import com.pengjia.data.backtest.core.Account;
import com.pengjia.data.backtest.core.Deal;
import com.pengjia.data.backtest.core.Trader;
import com.pengjia.data.backtest.core.deal.DealStatus;

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
        account.setCash(account.getCash() - order.value());
        Deal deal = new Deal();
        deal.deposit = order.value();
        deal.minDeposit = 0;
        deal.num = order.num;
        deal.setPrice(order.price);
        deal.prices.price = order.price;
        deal.profit = Deal.profit(deal, order.price);
        deal.status = DealStatus.UNCOMPLETE;
        deal.symbol = order.symbol;
        deal.type = order.type;
        account.addDeal(deal);
    }
}

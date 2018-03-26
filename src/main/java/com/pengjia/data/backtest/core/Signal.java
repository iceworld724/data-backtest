package com.pengjia.data.backtest.core;

public enum Signal {

    NULL(null, null);

    private String name;
    private SignalCalculator calculator;

    private Signal(String name, SignalCalculator calculator) {
        this.name = name;
        this.calculator = calculator;
    }

    public String getName() {
        return name;
    }

    public SignalCalculator getCalculator() {
        return calculator;
    }
}

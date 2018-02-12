package com.pengjia.data.backtest.core;

import java.util.EnumMap;
import java.util.Map;

public class DataUnit {

    private float open;
    private float close;
    private float high;
    private float low;
    private float vol;
    private Map<Signal, Float> signals = new EnumMap<>(Signal.class);

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getVol() {
        return vol;
    }

    public void setVol(float vol) {
        this.vol = vol;
    }

    public Map<Signal, Float> getSignals() {
        return signals;
    }

    public void setSignals(Map<Signal, Float> signals) {
        this.signals = signals;
    }
    
    public Float getSignal(Signal signal) {
        return signals.get(signal);
    }
}

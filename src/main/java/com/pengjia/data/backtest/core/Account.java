package com.pengjia.data.backtest.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private float cash = 0f;
    private Map<String, List<Position>> positions
            = new HashMap<String, List<Position>>();
    private Map<String, Float> deposits
            = new HashMap<String, Float>();

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public Map<String, List<Position>> getPositions() {
        return positions;
    }

    public void setPositions(Map<String, List<Position>> positions) {
        this.positions = positions;
    }

    public Map<String, Float> getDeposits() {
        return deposits;
    }

    public void setDeposits(Map<String, Float> deposits) {
        this.deposits = deposits;
    }

    public void addPosition(Position position) {
        List<Position> symbolPositions = positions.get(position.symbol);
        if (symbolPositions == null) {
            symbolPositions = new ArrayList<Position>();
        }
        symbolPositions.add(position);
        positions.put(position.symbol, symbolPositions);
    }
}

package com.pengjia.data.backtest.core;

import com.pengjia.data.backtest.core.position.PositionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Account {

    private float cash = 0f;
    private Map<Code, List<Position>> positions = new HashMap<>();
    private Map<Code, Float> deposits = new HashMap<>();

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public Map<Code, List<Position>> getPositions() {
        return positions;
    }

    public void setPositions(Map<Code, List<Position>> positions) {
        this.positions = positions;
    }

    public Map<Code, Float> getDeposits() {
        return deposits;
    }

    public void setDeposits(Map<Code, Float> deposits) {
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

    public float value(Data data) {
        return cash + (float) positions.keySet().stream().mapToDouble(
                symbol -> {
                    List<Position> list = positions.get(symbol);
                    return list.stream().mapToDouble(p -> p.value(data)).sum();
                }
        ).sum();
    }

    public List<Position> getPositions(Code symbol, PositionType type) {
        List<Position> positionList = positions.get(symbol);
        return positionList == null ? null : positions.get(symbol).stream()
                .filter(d -> d.type.equals(type))
                .collect(Collectors.toList());
    }
}

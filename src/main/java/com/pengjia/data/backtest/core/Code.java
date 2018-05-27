package com.pengjia.data.backtest.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code implements Comparable<Code> {

    private static Pattern PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");

    private String symbol;
    private int month;

    public Code(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.find()) {
            symbol = matcher.group(1);
            month = Integer.parseInt(matcher.group(2));
        }
    }

    public Code(String symbol, int month) {
        this.symbol = symbol;
        this.month = month;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public int compareTo(Code o) {
        if (this.symbol.equals(o.symbol)) {
            return Integer.compare(this.month, o.month);
        } else {
            return Integer.compare(this.symbol.hashCode(), o.symbol.hashCode());
        }
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

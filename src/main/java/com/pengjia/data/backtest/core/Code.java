package com.pengjia.data.backtest.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code implements Comparable<Code> {
    
    private static Pattern PATTERN = Pattern.compile("([A-Z]+)([0-9]+)");
    
    private String symbol;
    private int month;
    
    public Code(String str){
        Matcher matcher = PATTERN.matcher(str);
        if(matcher.find()){
            symbol = matcher.group(1);
            month = Integer.parseInt(matcher.group(2));
        }
    }
    
    public Code(String symbol, int month){
        this.symbol = symbol;
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
}

package com.pengjia.data.backtest.core.data;

import com.pengjia.data.backtest.core.Data;

public interface DataLoader {

    Data load() throws Exception;
}

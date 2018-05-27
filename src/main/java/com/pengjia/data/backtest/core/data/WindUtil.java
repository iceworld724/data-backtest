package com.pengjia.data.backtest.core.data;

import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import scala.Tuple2;

public class WindUtil {

    private static final DateTimeFormatter FORMAT
            = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public static Tuple2<DataUnit, DateTime> csvRecord2DataUnit(CSVRecord record) {
        DateTime time = FORMAT.parseDateTime(record.get(""));
        DataUnit unit = new DataUnit();
        unit.setClose(Float.parseFloat(record.get("close")));
        unit.setHigh(Float.parseFloat(record.get("high")));
        unit.setLow(Float.parseFloat(record.get("low")));
        unit.setOpen(Float.parseFloat(record.get("open")));
        unit.setVol(Float.parseFloat(record.get("volume")));
        unit.setAmount(Float.parseFloat(record.get("amount")));
        unit.setOi(Float.parseFloat(record.get("position")));
        return new Tuple2<>(unit, time);
    }
}

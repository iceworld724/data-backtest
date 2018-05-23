package com.pengjia.data.backtest.core.data;

import com.pengjia.data.backtest.core.Data;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.joda.time.DateTime;
import scala.Tuple2;

public class WindDirCSVDataLoader implements DataLoader {

    private static final DateTimeFormatter FORMAT
            = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private final File dir;

    public WindDirCSVDataLoader(File dir) {
        this.dir = dir;
    }

    @Override
    public Data load() throws IOException {
        Data data = new Data();

        String[] files = dir.list();
        for (String file : files) {
            String symbol = file.substring(0, file.lastIndexOf("."));
            final Reader reader = new InputStreamReader(new BOMInputStream(
                    new FileInputStream(new File(dir, file))), "UTF-8");
            final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
            try {
                for (final CSVRecord record : parser) {
                    Tuple2<DataUnit, DateTime> tuple2 = toDataUnits(record);
                    data.addDataUnit(tuple2._2, symbol, tuple2._1);
                }
            } finally {
                parser.close();
                reader.close();
            }
        }
        return data;
    }

    private Tuple2<DataUnit, DateTime> toDataUnits(CSVRecord record) {
        DateTime time = FORMAT.parseDateTime(record.get(""));
        DataUnit unit = new DataUnit();
        unit.setClose(Float.parseFloat(record.get("close")));
        unit.setHigh(Float.parseFloat(record.get("high")));
        unit.setLow(Float.parseFloat(record.get("low")));
        unit.setOpen(Float.parseFloat(record.get("open")));
        unit.setVol(Float.parseFloat(record.get("volume")));
        unit.setAmount(Float.parseFloat(record.get("amount")));
        return new Tuple2<>(unit, time);
    }
}

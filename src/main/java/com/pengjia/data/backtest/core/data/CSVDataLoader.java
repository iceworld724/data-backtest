package com.pengjia.data.backtest.core.data;

import com.pengjia.data.backtest.core.Data;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CSVDataLoader implements DataLoader {

    private static final DateTimeFormatter FORMAT
            = DateTimeFormat.forPattern("yyyy/MM/dd");

    private final String fileUrl;
    private final String symbol;

    public CSVDataLoader(String symbol, String fileUrl) {
        this.fileUrl = fileUrl;
        this.symbol = symbol;
    }

    @Override
    public Data load() throws IOException {
        final URL url = new URL(fileUrl);
        final Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
        final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
        Data data = new Data(symbol);
        try {
            for (final CSVRecord record : parser) {
                data.addDataUnit(toDataUnit(record));
            }
        } finally {
            parser.close();
            reader.close();
        }
        data.sort();
        return data;
    }

    private DataUnit toDataUnit(CSVRecord record) {
        DataUnit unit = new DataUnit();
        unit.setBeginTime(FORMAT.parseDateTime(record.get("时间")));
        unit.setClose(Float.parseFloat(record.get("收盘")));
        unit.setHigh(Float.parseFloat(record.get("最高")));
        unit.setLow(Float.parseFloat(record.get("最低")));
        unit.setOpen(Float.parseFloat(record.get("开盘")));
        unit.setVol(Integer.parseInt(record.get("成交量")));
        return unit;
    }
}

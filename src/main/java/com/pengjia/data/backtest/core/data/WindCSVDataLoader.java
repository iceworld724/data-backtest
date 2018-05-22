package com.pengjia.data.backtest.core.data;

import com.pengjia.data.backtest.core.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WindCSVDataLoader implements DataLoader {

    private static final DateTimeFormatter FORMAT
            = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private final String fileUrl;
    private final String symbol;

    public WindCSVDataLoader(String symbol, String fileUrl) {
        this.fileUrl = fileUrl;
        this.symbol = symbol;
    }

    @Override
    public Data load() throws IOException {
        final URL url = new URL(fileUrl);
        final Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
        final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
        Data data = new Data();
        try {
            for (final CSVRecord record : parser) {
                data.addDataUnits(toDataUnits(record));
            }
        } finally {
            parser.close();
            reader.close();
        }
        data.sort();
        return data;
    }

    private DataUnits toDataUnits(CSVRecord record) {
        DataUnits dataUnits = new DataUnits();
        dataUnits.setBeginTime(FORMAT.parseDateTime(record.get("")));
        Map<String, DataUnit> units = new HashMap<String, DataUnit>();
        DataUnit unit = new DataUnit();
        unit.setClose(Float.parseFloat(record.get("close")));
        unit.setHigh(Float.parseFloat(record.get("high")));
        unit.setLow(Float.parseFloat(record.get("low")));
        unit.setOpen(Float.parseFloat(record.get("open")));
        unit.setVol(Float.parseFloat(record.get("volume")));
        unit.setAmount(Float.parseFloat(record.get("amount")));
        units.put(symbol, unit);
        dataUnits.setDataUnit(symbol, unit);
        return dataUnits;
    }
}

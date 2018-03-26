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

public class CSVDataLoader implements DataLoader {

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

        return data;
    }

    private DataUnit toDataUnit(CSVRecord record) {
        DataUnit unit = new DataUnit();
        unit.setClose(Float.parseFloat(record.get("close")));
        unit.setHigh(Float.parseFloat(record.get("high")));
        unit.setLow(Float.parseFloat(record.get("low")));
        unit.setOpen(Float.parseFloat(record.get("open")));
        unit.setVol(Integer.parseInt(record.get("vol")));
        return unit;
    }
}

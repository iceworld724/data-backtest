package com.pengjia.data.backtest.core.data;

import com.pengjia.data.backtest.core.Code;
import com.pengjia.data.backtest.core.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import org.joda.time.DateTime;
import scala.Tuple2;

public class WindCSVDataLoader implements DataLoader {

    private final String fileUrl;
    private final Code symbol;

    public WindCSVDataLoader(Code symbol, String fileUrl) {
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
                if (record.get("open").isEmpty()) {
                    continue;
                }
                Tuple2<DataUnit, DateTime> tuple2 = WindUtil.csvRecord2DataUnit(record);
                data.addDataUnit(tuple2._2, symbol, tuple2._1);
            }
        } finally {
            parser.close();
            reader.close();
        }
        return data;
    }
}

package com.pengjia.data.backtest.core.data;

import com.pengjia.data.backtest.core.Code;
import com.pengjia.data.backtest.core.Data;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.joda.time.DateTime;
import scala.Tuple2;

public class WindDirCSVDataLoader implements DataLoader {

    private final File dir;

    public WindDirCSVDataLoader(File dir) {
        this.dir = dir;
    }

    @Override
    public Data load() throws IOException {
        Data data = new Data();

        String[] files = dir.list();
        for (String file : files) {
            Code symbol = new Code(file.substring(0, file.lastIndexOf(".")));
            final Reader reader = new InputStreamReader(new BOMInputStream(
                    new FileInputStream(new File(dir, file))), "UTF-8");
            final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
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
        }
        return data;
    }
}

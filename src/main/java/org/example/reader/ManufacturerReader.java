package org.example.reader;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.domain.Manufacturer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class ManufacturerReader {
    public List<Manufacturer> readCsvFile() throws FileNotFoundException {
        FileReader reader = new FileReader("manufacturers.csv");
        CsvToBean<Manufacturer> csvToBean = new CsvToBeanBuilder<Manufacturer>(reader)
                .withType(Manufacturer.class)
                .withSeparator(';')
                .build();

        return csvToBean.parse();
    }
}

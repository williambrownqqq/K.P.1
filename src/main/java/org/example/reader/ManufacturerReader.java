package org.example.reader;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.domain.Manufacturer;
import org.example.factoryReader.ReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ManufacturerReader {
    private static ManufacturerReader instance;
    private ManufacturerReader(){
    }
    public static ManufacturerReader getInstance(){
        if (instance == null) {
            instance = new ManufacturerReader();
        }
        return instance;
    }
    public List<Manufacturer> readCsvFile() throws IOException {
//        FileReader reader = new FileReader("manufacturers.csv");
        ReaderFactory factory = new ReaderFactory();
        FileReader reader = factory.createReader("manufacturers.csv").getFileReader();
        CsvToBean<Manufacturer> csvToBean = new CsvToBeanBuilder<Manufacturer>(reader)
                .withType(Manufacturer.class)
                .withSeparator(';')
                .build();

        return csvToBean.parse();
    }
}

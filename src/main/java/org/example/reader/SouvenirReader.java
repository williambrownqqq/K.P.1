package org.example.reader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.factoryReader.Reader;
import org.example.factoryReader.ReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SouvenirReader {
    private static SouvenirReader instance;

    private SouvenirReader(){
    }

    public static SouvenirReader getInstance(){
        if(instance == null){
            instance = new SouvenirReader();
        }
        return instance;
    }

    public List<Souvenir> readCsvFile() throws IOException {
//        FileReader reader = new FileReader("souvenirs.csv");
        ReaderFactory factory = new ReaderFactory();
        FileReader reader = factory.createReader("souvenirs.csv").getFileReader();
        CsvToBean<Souvenir> csvToBean = new CsvToBeanBuilder<Souvenir>(reader)
                .withType(Souvenir.class)
                .withSeparator(';')
                .build();

        return csvToBean.parse();
    }
}

package org.example.reader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class SouvenirReader {
    public List<Souvenir> readCsvFile() throws FileNotFoundException {
        FileReader reader = new FileReader("souvenirs.csv");
        CsvToBean<Souvenir> csvToBean = new CsvToBeanBuilder<Souvenir>(reader)
                .withType(Souvenir.class)
                .withSeparator(';')
                .build();

        return csvToBean.parse();
    }
}

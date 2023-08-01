package org.example.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Souvenir;
import org.example.factoryWriter.WriterFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class SouvenirWriter {
    private static SouvenirWriter instance;
    private SouvenirWriter(){

    }
    public static SouvenirWriter getInstance() {
        if (instance == null) {
            // Create the instanc   e only if it doesn't exist
            instance = new SouvenirWriter();
        }
        return instance;
    }
    public void doWrite() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        WriterFactory writerFactory = new WriterFactory();
        Writer writer = writerFactory.createWrite("souvenirs.csv").getFileWriter();
        String[] header = new String[]{"id", "name", "manufacturerId", "productionDate", "price"};
        //Writer writer = new FileWriter("souvenirs.csv");
        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(writer)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();
        Souvenir.SouvenirBuilder builder = new Souvenir.SouvenirBuilder();
        Souvenir souvenir1 = builder.id(1L)
                .name("phone")
                .manufacturer("Apple")
                .productionDate("2023")
                .price(1000)
                .build();
        Souvenir souvenir2 = builder.id(2L)
                .name("car")
                .manufacturer("Ford")
                .productionDate("2022")
                .price(5000)
                .build();
        Souvenir souvenir3 = builder.id(3L)
                .name("plane")
                .manufacturer("Antonov")
                .productionDate("2021")
                .price(9999)
                .build();
//        List<Souvenir> SouvenirList = List.of(
//                new Souvenir(1, "phone", "Apple", "2023", 1000),
//                new Souvenir(2, "car", "Ford", "2022", 5000),
//                new Souvenir(3, "plane", "Antonov", "2021", 9999)
//        );
        List<Souvenir> SouvenirList = List.of(
                souvenir1,
                souvenir2,
                souvenir3
        );
        beanToCsv.write(SouvenirList);
        writer.close();
    }
}

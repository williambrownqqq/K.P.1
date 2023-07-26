package org.example.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Souvenir;
import org.example.domainBuilder.ConcreteSouvenirBuilder;
import org.example.domainBuilder.SouvenirBuilder;
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
            // Create the instance only if it doesn't exist
            instance = new SouvenirWriter();
        }
        return instance;
    }
    public void doWrite() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
//        CSVWriter writer = new CSVWriter(new FileWriter("souvenirs.csv"),
//                ';',
//                '"',
//                '\\',
//                CSVWriter.DEFAULT_LINE_END);
//        List<String[]> therows = new ArrayList<>();
//        String[] header = new String[]{"id", "name", "manufacturer", "productionDate", "price"};
//        therows.add(header);
//
//        String[] souvenir1 = new String[]{"1", "phone", "Apple", "2023", "1000"};
//        String[] souvenir2 = new String[]{"2", "car", "Ford", "2022", "5000"};
//        String[] souvenir3 = new String[]{"3", "plane", "Antonov", "2021", "9999"};
//        therows.add(souvenir1);
//        therows.add(souvenir2);
//        therows.add(souvenir3);
//
//        writer.writeAll(therows);
//        writer.close();

        WriterFactory writerFactory = new WriterFactory();
        Writer writer = writerFactory.createWrite("souvenirs.csv").getFileWriter();
        //Writer writer = new FileWriter("souvenirs.csv");
        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(writer)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();
        SouvenirBuilder builder = new ConcreteSouvenirBuilder();
        Souvenir souvenir1 = builder.setId(1)
                .setName("phone")
                .setManufacturer("Apple")
                .setProductionDate("2023")
                .setPrice(1000)
                .build();
        Souvenir souvenir2 = builder.setId(2)
                .setName("car")
                .setManufacturer("Ford")
                .setProductionDate("2022")
                .setPrice(5000)
                .build();
        Souvenir souvenir3 = builder.setId(3)
                .setName("plane")
                .setManufacturer("Antonov")
                .setProductionDate("2021")
                .setPrice(9999)
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

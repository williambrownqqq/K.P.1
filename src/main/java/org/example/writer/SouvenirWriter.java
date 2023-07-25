package org.example.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class SouvenirWriter {

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
        Writer writer = new FileWriter("souvenirs.csv");
        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(writer)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();
        List<Souvenir> SouvenirList = List.of(
                new Souvenir(1, "phone", "Apple", "2023", 1000),
                new Souvenir(2, "car", "Ford", "2022", 5000),
                new Souvenir(3, "plane", "Antonov", "2021", 9999)
        );
        beanToCsv.write(SouvenirList);
        writer.close();
    }
}

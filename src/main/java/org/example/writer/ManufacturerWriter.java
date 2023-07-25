package org.example.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ManufacturerWriter {

    public void writeManufacturers() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        Writer writer = new FileWriter("manufacturers.csv");
        StatefulBeanToCsv<Manufacturer> beanToCsv = new StatefulBeanToCsvBuilder<Manufacturer>(writer)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();
        List<Manufacturer> manufacturerList = List.of(
                new Manufacturer(1, "Apple", "USA"),
                new Manufacturer(2, "Samsung", "South Korea")
        );
        beanToCsv.write(manufacturerList);
        writer.close();
    }
}

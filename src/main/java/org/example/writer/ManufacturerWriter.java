package org.example.writer;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domainBuilder.ConcreteManufaturerBuilder;
import org.example.domainBuilder.ManufacturerBuilder;
import org.example.factoryWriter.WriterFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ManufacturerWriter { // implement Singleton
    // Private static instance variable for Singleton
    private static ManufacturerWriter instance;

    // Private constructor to prevent instantiation from other classes
    private ManufacturerWriter() {
        // Optional: You can add initialization code here
    }

    // Public static method to provide access to the instance
    public static ManufacturerWriter getInstance() {
        if (instance == null) {
            // Create the instance only if it doesn't exist
            instance = new ManufacturerWriter();
        }
        return instance;
    }

    public void writeManufacturers() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        WriterFactory writerFactory = new WriterFactory();
        Writer writer = writerFactory.createWrite("manufacturers.csv").getFileWriter();
        //Writer writer = new FileWriter("manufacturers.csv");
        StatefulBeanToCsv<Manufacturer> beanToCsv = new StatefulBeanToCsvBuilder<Manufacturer>(writer)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        ManufacturerBuilder builder = new ConcreteManufaturerBuilder();
        Manufacturer manufacturer1 =  builder.setId(1)
                                                .setName("Apple")
                                                .setCountry("USA")
                                                .build();
        Manufacturer manufacturer2 =  builder.setId(2)
                                                .setName("Samsung")
                                                .setCountry("South Korea")
                                                .build();
        List<Manufacturer> manufacturerList = List.of(
                manufacturer1,
                manufacturer2
        );
//        List<Manufacturer> manufacturerList = List.of(
//                new Manufacturer(1, "Apple", "USA"),
//                new Manufacturer(2, "Samsung", "South Korea")
//        );
        beanToCsv.write(manufacturerList);
        writer.close();
    }
}

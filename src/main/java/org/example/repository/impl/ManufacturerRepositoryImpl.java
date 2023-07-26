package org.example.repository.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domainBuilder.ConcreteManufaturerBuilder;
import org.example.domainBuilder.ManufacturerBuilder;
import org.example.factoryWriter.WriterFactory;
import org.example.repository.MaufacturerRepository;
import org.example.writer.ManufacturerWriter;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.example.service.SouvenirService.IDcounterManufacturer;

public class ManufacturerRepositoryImpl implements MaufacturerRepository {
    @Override
    public Manufacturer getById(Integer id) {
        return null;
    }

    @Override
    public List<Manufacturer> getAll() {
        return null;
    }

    @Override
    public void add() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        WriterFactory writerFactory = new WriterFactory();
        FileWriter writer = writerFactory.createWrite("manufacturers.csv").getFileWriter(true);

        StatefulBeanToCsv<Manufacturer> beanToCsv = new StatefulBeanToCsvBuilder<Manufacturer>(writer)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter country: ");
        String country = scanner.nextLine();

        ManufacturerBuilder builder = new ConcreteManufaturerBuilder();
        IDcounterManufacturer += 1;
        Manufacturer man =  builder.setId(IDcounterManufacturer)
                .setName(name)
                .setCountry(country)
                .build();

        List<Manufacturer> manufacturerList = List.of(
                man
        );
        beanToCsv.write(manufacturerList);
        writer.close();

    }

    @Override
    public void update(Manufacturer manufacturer) {

    }

    @Override
    public void delete(int id) {

    }
}

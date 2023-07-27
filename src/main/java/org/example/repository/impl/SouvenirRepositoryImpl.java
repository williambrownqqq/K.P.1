package org.example.repository.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.factoryWriter.WriterForManufacturer;
import org.example.reader.SouvenirReader;
import org.example.repository.SouvenirRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SouvenirRepositoryImpl implements SouvenirRepository {
    @Override
    public Souvenir getById(Integer id) throws IOException {
        List<Souvenir> souvenirs = getAll();
        return souvenirs.get(id);
    }

    @Override
    public List<Souvenir> getAll() throws IOException {
        List<Souvenir> souvenirs = SouvenirReader.getInstance().readCsvFile();
        return souvenirs;
    }

    @Override
    public void add() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = getAll();
        int lastId = souvenirs.size();
        System.out.println(lastId);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Enter productionDate: ");
        String productionDate = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
//        String name = "max";
//        String manufacturer = "ua";
//        String productionDate = "1111";
//        double price = 4444;

        souvenirs.add(new Souvenir(lastId++, name, manufacturer, productionDate, price));
        saveAll(souvenirs);
    }

    @Override
    public void update(int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = getAll();
        delete(id);
        add();
    }

    @Override
    public void delete(int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = getAll();
        souvenirs.removeIf(souvenir -> souvenir.getId() == id);
        saveAll(souvenirs);
    }

    @Override
    public void saveAll(List<Souvenir> souvenirs) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        WriterForManufacturer writer = new WriterForManufacturer();
        FileWriter fileWriter = writer.getFileWriter();

        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(fileWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(souvenirs);
        fileWriter.close();
    }
}

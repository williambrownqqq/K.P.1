package org.example.repository.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.domainBuilder.ConcreteManufaturerBuilder;
import org.example.domainBuilder.ManufacturerBuilder;
import org.example.factoryWriter.WriterFactory;
import org.example.factoryWriter.WriterForManufacturer;
import org.example.reader.ManufacturerReader;
import org.example.repository.MaufacturerRepository;
import org.example.repository.SouvenirRepository;
import org.example.writer.ManufacturerWriter;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ManufacturerRepositoryImpl implements MaufacturerRepository {
    @Override
    public Manufacturer getById(Integer id) throws IOException {
        List<Manufacturer> manufacturerList = getAll();
        return manufacturerList.get(id);
    }

    @Override
    public List<Manufacturer> getAll() throws IOException {
        List<Manufacturer> manufacturerList = ManufacturerReader.getInstance().readCsvFile();
        return manufacturerList;
    }

    @Override
    public void add() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Manufacturer> manufacturerList = getAll();
        int lastId = manufacturerList.size();
        System.out.println(lastId);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter country: ");
        String country = scanner.nextLine();
//            String name = "max";
//            String country = "ua";
        manufacturerList.add(new Manufacturer(lastId++, name, country));

//        WriterFactory writerFactory = new WriterFactory();
//        FileWriter writer = writerFactory.createWrite("manufacturers.csv").getFileWriter(true);
//
//        StatefulBeanToCsv<Manufacturer> beanToCsv = new StatefulBeanToCsvBuilder<Manufacturer>(writer)
//                .withSeparator(';')
//                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
//                .withOrderedResults(true)
//                .build();
//
//        beanToCsv.write(manufacturerList);
//        writer.close();
        saveAll(manufacturerList);
    }

    @Override
    public void update(int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Manufacturer> allManufacturers = getAll();
        delete(id);
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter name: ");
//        String name = scanner.nextLine();
//
//        System.out.print("Enter country: ");
//        String country = scanner.nextLine();
        add();
    }

    @Override
    public void delete(int id) throws CsvDataTypeMismatchException, IOException, CsvRequiredFieldEmptyException {
        List<Manufacturer> allManufacturers = getAll();
        allManufacturers.removeIf(manufacturer -> manufacturer.getId() == id);
        saveAll(allManufacturers);
    }
    @Override
    public void saveAll(List<Manufacturer> allManufacturers) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        WriterForManufacturer writer = new WriterForManufacturer();
        FileWriter fileWriter = writer.getFileWriter();

        StatefulBeanToCsv<Manufacturer> beanToCsv = new StatefulBeanToCsvBuilder<Manufacturer>(fileWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(allManufacturers);
        fileWriter.close();
    }

    public List<Manufacturer> getManufacturersByPrice() throws IOException {

        List<Manufacturer> manufacturers = getAll();
        SouvenirRepository repository = new SouvenirRepositoryImpl();
        List<Souvenir> souvenirs = repository.getAll();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        List<Souvenir> filteredSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getPrice() < price)
                .toList();

//        Set<Manufacturer> filteredManufacturers = filteredSouvenirs
//                .stream()
////                .map(Souvenir::getManufacturer)
//                .filter(souvenirManufacurer -> manufacturers
//                        .stream()
//                        .anyMatch(manufacturer -> souvenirManufacurer.getManufacturer().equalsIgnoreCase(manufacturer.getName())))
//                .collect(Collectors.toSet());

        Set<String> manufacturerNamesFromFilteredSouvenirs = filteredSouvenirs
                .stream()
                .map(Souvenir::getManufacturer)
                .collect(Collectors.toSet());

        Set<Manufacturer> filteredManufacturers = manufacturers
                .stream()
                .filter(manufacturer -> manufacturerNamesFromFilteredSouvenirs
                        .stream()
                        .anyMatch(name -> manufacturer.getName().equalsIgnoreCase(name)))
                .collect(Collectors.toSet());

        return new ArrayList<>(filteredManufacturers);
    }
}

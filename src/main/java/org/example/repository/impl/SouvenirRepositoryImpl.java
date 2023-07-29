package org.example.repository.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.factoryWriter.WriterForManufacturer;
import org.example.factoryWriter.WriterForSouvenir;
import org.example.reader.SouvenirReader;
import org.example.repository.SouvenirRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Souvenir> getSouvenirsByManufacturer() throws IOException {
        List<Souvenir> souvenirs = getAll();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();
//        souvenirs.stream()
//                .filter(souvenir -> souvenir.getManufacturer().equalsIgnoreCase(manufacturer))
//                .forEach(System.out::println);
        List<Souvenir> filteredSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getManufacturer().equalsIgnoreCase(manufacturer))
                .toList();
        return filteredSouvenirs;
    }

    public List<Souvenir> getSouvenirsByCountry() throws IOException {
        List<Souvenir> souvenirs = getAll();
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl();
        List<Manufacturer> manufacturers = repository.getAll();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter сountry: ");
        String country = scanner.nextLine();
        List<Manufacturer> filteredManufacturers = manufacturers
                .stream()
                .filter(manufacturer -> manufacturer.getCountry().equalsIgnoreCase(country))
                .toList();
        List<Souvenir> filteredSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> filteredManufacturers
                                .stream()
                                .anyMatch(manufacturer -> souvenir.getManufacturer().equals(manufacturer.getName()))
                        )
                .toList();
        return filteredSouvenirs;
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
        WriterForSouvenir writer = new WriterForSouvenir();
        FileWriter fileWriter = writer.getFileWriter();

        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(fileWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(souvenirs);
        fileWriter.close();
    }

    public Map<String, List<Souvenir>>  souvenirsByYear() throws IOException {
        List<Souvenir> souvenirs = getAll();
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl();
        List<Manufacturer> manufacturers = repository.getAll();
        Set<String> years = souvenirs
                .stream()
                .map(souvenir -> souvenir.getProductionDate())
                .collect(Collectors.toSet());

        Map<String, List<Souvenir>> souvenirsByYear = souvenirs
                .stream()
                .collect(Collectors.groupingBy(Souvenir::getProductionDate));

        return souvenirsByYear;
    }

    public void printSouvenirsWithYear() throws IOException {
        Map<String, List<Souvenir>> souvenirsByYear = souvenirsByYear();

        for (Map.Entry<String, List<Souvenir>> entry : souvenirsByYear.entrySet()) {
            String souvenirName = entry.getKey();
            List<Souvenir> souvenirs = entry.getValue();

            System.out.println("Year: " + souvenirName);
            System.out.println("Souvenirs: ");
            for (Souvenir souvenir : souvenirs) {
                System.out.println("- " + souvenir.getName() + " (" + souvenir.getPrice() + ")");
            }
            System.out.println(); // Add a blank line between each manufacturer's souvenirs
        }

    }
}

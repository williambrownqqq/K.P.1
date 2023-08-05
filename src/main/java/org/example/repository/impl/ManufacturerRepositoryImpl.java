package org.example.repository.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.repository.ManufacturerRepository;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import org.example.writer.factory.EntityWriterFactory;


public class ManufacturerRepositoryImpl implements ManufacturerRepository {
    private final String path;
    private final EntityReader<Souvenir> souvenirEntityReader;
    private final EntityReader<Manufacturer> manufacturerEntityReader;

    public ManufacturerRepositoryImpl(String path,EntityReader<Manufacturer> manufacturerEntityReader,EntityReader<Souvenir> souvenirEntityReader) {
        this.path = path;
        this.manufacturerEntityReader = manufacturerEntityReader;
        this.souvenirEntityReader = souvenirEntityReader;
    }

    @Override
    public Manufacturer getById(Integer id) throws IOException {
        List<Manufacturer> manufacturerList = getAll();
        return manufacturerList.get(id);
    }

    @Override
    public List<Manufacturer> getAll() throws IOException {
        List<Manufacturer> manufacturerList = manufacturerEntityReader.readCsvFile();
        return manufacturerList;
    }

    @Override
    public void add(String name,String country) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Manufacturer> manufacturers = getAll();
        int lastId = manufacturers.size();

        manufacturers.add(new Manufacturer((long) ++lastId, name, country));
        saveAll(manufacturers);
    }

    @Override
    public void delete(long id) throws CsvDataTypeMismatchException, IOException, CsvRequiredFieldEmptyException {
        List<Manufacturer> allManufacturers = getAll();
        allManufacturers.removeIf(manufacturer -> manufacturer.getId().equals(id)); // Remove the element at the specified index
        saveAll(allManufacturers);


    }
    @Override
    public void saveAll(List<Manufacturer> allManufacturers) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FileWriter fileWriter = EntityWriterFactory.createEntityWriter(path, Manufacturer.class).getFileWriter();

        StatefulBeanToCsv<Manufacturer> beanToCsv = new StatefulBeanToCsvBuilder<Manufacturer>(fileWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(allManufacturers);
        fileWriter.close();
    }

    @Override
    public List<Souvenir> deleteSouvenirsByManufacturer(List<Souvenir> souvenirs, List<Souvenir> matchingSouvenirs) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        souvenirs.removeAll(matchingSouvenirs);
        return souvenirs;
    }
    @Override
    public List<Manufacturer> getManufacturersByPrice(double price, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) throws IOException {
        List<Souvenir> filteredSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getPrice() < price)
                .toList();

        Set<String> manufacturerNamesFromFilteredSouvenirs = filteredSouvenirs
                .stream()
                .map(Souvenir::getManufacturer)
                .collect(Collectors.toSet());

        Set<Manufacturer> filteredManufacturers = manufacturers
                .stream()
                .filter(manufacturer -> manufacturerNamesFromFilteredSouvenirs
                        .contains(manufacturer.getName()))
                .collect(Collectors.toSet());

        return new ArrayList<>(filteredManufacturers);
    }
    @Override
    public Map<String, List<Souvenir>> getAllManufacturersWithSouvenirs(List<Souvenir> souvenirs) throws IOException {
        Map<String, List<Souvenir>> manufacturersWithSouvenirs = souvenirs.stream()
                .collect(Collectors.groupingBy(Souvenir::getManufacturer));

        return manufacturersWithSouvenirs;
    }
    @Override
    public List<Manufacturer> getManufacturersBySouvenirAndYear(String name, String productionDate, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) throws IOException {

        // Filter the souvenirs based on the provided name and production year
        List<Souvenir> filteredSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getName().equalsIgnoreCase(name) &&
                        souvenir.getProductionDate().equalsIgnoreCase(productionDate))
                .toList();

        // Extract the names of the manufacturers associated with the filtered souvenirs
        List<String> manufacturerNames = filteredSouvenirs.stream()
                .map(Souvenir::getManufacturer)
                .collect(Collectors.toList());

        // Filter the manufacturers based on the extracted names
        List<Manufacturer> filteredManufacturers = manufacturers.stream()
                .filter(manufacturer -> manufacturerNames.contains(manufacturer.getName()))
                .collect(Collectors.toList());
        return filteredManufacturers;
    }


}

package org.example.repository.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.repository.SouvenirRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.writer.factory.EntityWriterFactory.createEntityWriter;

public class SouvenirRepositoryImpl implements SouvenirRepository {

    private final String path;
    private final EntityReader<Souvenir> souvenirEntityReader;
    private final EntityReader<Manufacturer> manufacturerEntityReader;

    public SouvenirRepositoryImpl(String path, EntityReader<Souvenir> souvenirEntityReader, EntityReader<Manufacturer> manufacturerEntityReader) {
        this.path = path;
        this.souvenirEntityReader = souvenirEntityReader;
        this.manufacturerEntityReader = manufacturerEntityReader;
    }

    @Override
    public Souvenir getById(Integer id) throws IOException {
        List<Souvenir> souvenirs = getAll();
        return souvenirs.get(id);
    }

    @Override
    public List<Souvenir> getAll() throws IOException {
        List<Souvenir> souvenirs = souvenirEntityReader.readCsvFile();
        return souvenirs;
    }

    public List<Souvenir> getSouvenirsByManufacturer(String manufacturer) throws IOException {
        List<Souvenir> souvenirs = getAll();
        List<Souvenir> filteredSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getManufacturer().equalsIgnoreCase(manufacturer))
                .toList();
        return filteredSouvenirs;
    }

    public List<Souvenir> getSouvenirsByCountry(String country) throws IOException {
        List<Souvenir> souvenirs = getAll();
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl(path, manufacturerEntityReader, souvenirEntityReader);
        List<Manufacturer> manufacturers = repository.getAll();


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
    public void add(String name,String manufacturer, String productionDate, double price) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = getAll();
        Long lastId = (long) souvenirs.size();

        souvenirs.add(new Souvenir(lastId, name, manufacturer, productionDate, price));
        saveAll(souvenirs);
    }

    @Override
    public void delete(long id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = getAll();
        souvenirs.removeIf(souvenir -> souvenir.getId().equals(id));
        saveAll(souvenirs);
    }

    @Override
    public void saveAll(List<Souvenir> souvenirs) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FileWriter fileWriter = createEntityWriter(path, Souvenir.class).getFileWriter();

        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(fileWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(souvenirs);
        fileWriter.close();
    }

    public Map<String, List<Souvenir>> souvenirsByYear(List<Souvenir> souvenirs, List<Manufacturer> manufacturers) throws IOException {
        Set<String> years = souvenirs
                .stream()
                .map(souvenir -> souvenir.getProductionDate())
                .collect(Collectors.toSet());

        Map<String, List<Souvenir>> souvenirsByYear = souvenirs
                .stream()
                .collect(Collectors.groupingBy(Souvenir::getProductionDate));

        return souvenirsByYear;
    }


}

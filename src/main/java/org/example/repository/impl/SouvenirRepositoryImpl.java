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
import java.util.stream.Collectors;
import org.example.writer.factory.EntityWriterFactory;

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
        return souvenirEntityReader.readCsvFile();
    }
    @Override
    public List<Souvenir> getSouvenirsByManufacturer(String manufacturer) throws IOException {
       return getAll()
                .stream()
                .filter(souvenir -> souvenir.getManufacturer().equalsIgnoreCase(manufacturer))
                .toList();
    }
    @Override
    public void add(Souvenir souvenir) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = getAll();
        if(souvenir.getId() == null) {
            long lastId = souvenirs.size();
            souvenir.setId(++lastId);
        }

        souvenirs.add(souvenir);
        saveAll(souvenirs);
    }

    @Override
    public void deleteAll(Iterable<Souvenir> entities) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        for (var souvenir:entities) {
            delete(souvenir.getId());
        }
    }

    @Override
    public void delete(Long id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        if(id == null) return;
        List<Souvenir> souvenirs = getAll();
        souvenirs.removeIf(souvenir -> souvenir.getId().equals(id));
        saveAll(souvenirs);
    }

    @Override
    public void saveAll(List<Souvenir> souvenirs) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FileWriter fileWriter = EntityWriterFactory.createEntityWriter(path, Souvenir.class).getFileWriter();

        StatefulBeanToCsv<Souvenir> beanToCsv = new StatefulBeanToCsvBuilder<Souvenir>(fileWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();

        beanToCsv.write(souvenirs);
        fileWriter.close();
    }


}

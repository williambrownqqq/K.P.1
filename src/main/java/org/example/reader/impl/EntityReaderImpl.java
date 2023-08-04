package org.example.reader.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.domain.IdentifiableEntity;
import org.example.domain.Manufacturer;
import org.example.reader.EntityReader;
import org.example.reader.factory.EntityReaderFactory;
import org.example.reader.factory.impl.EntityReaderFactoryImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.example.reader.factory.EntityReaderFactory.createEntityReader;

public class EntityReaderImpl<T extends IdentifiableEntity> implements EntityReader<T> {

    private final String path;
    private final Class<T> clazz;
//    private final EntityReaderFactory entityReaderFactory;
    public EntityReaderImpl(String path, Class<T> clazz) {
        this.path = path;
        this.clazz = clazz;
//        this.entityReaderFactory = new EntityReaderFactoryImpl();
    }

    @Override
    public List<T> readCsvFile() throws IOException {
        FileReader reader = createEntityReader(path, Manufacturer.class).getFileReader();
        try (reader) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withSeparator(';')
                    .build();
            return csvToBean.parse();
        }
    }

    @Override
    public T getById(Long id) throws IOException {
        return readCsvFile().stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public FileReader getFileReader() throws IOException {
        return new FileReader(path);
    }
}
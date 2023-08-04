package org.example.writer.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.IdentifiableEntity;
import org.example.domain.Manufacturer;
import org.example.writer.EntityWriter;
import org.example.writer.factory.EntityWriterFactory;
import org.example.writer.factory.impl.EntityWriterFactoryImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class EntityWriterImpl<T extends IdentifiableEntity> implements EntityWriter<T> {

    private final String path;
    private final Class<T> clazz;
    private final EntityWriterFactory entityWriterFactory;
    public EntityWriterImpl(String path, Class<T> clazz) {
        this.path = path;
        this.clazz = clazz;
        this.entityWriterFactory = new EntityWriterFactoryImpl();
    }


    @Override
    public void write(T entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(path, true)) {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(';')
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(true)
                    .build();
            beanToCsv.write(entity);
        }
    }

    @Override
    public void write(List<T> entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(path, true)) {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(';')
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(true)
                    .build();
            beanToCsv.write(entity);
        }
    }

    @Override
    public void override(List<T> entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new FileWriter(path)) {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(';')
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(true)
                    .build();
            beanToCsv.write(entity);
        }
    }

    @Override
    public FileWriter getFileWriter() throws IOException {
        return new FileWriter(path);
    }

    @Override
    public FileWriter getFileWriter(boolean append) throws IOException {
        return new FileWriter(path, append);
    }

}

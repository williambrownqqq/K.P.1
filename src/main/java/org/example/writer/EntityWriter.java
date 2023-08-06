package org.example.writer;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.IdentifiableEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface EntityWriter<T extends IdentifiableEntity> {
    FileWriter getFileWriter() throws IOException;
    FileWriter getFileWriter(boolean append) throws IOException;
    void write(T entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void write(List<T> entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void override(List<T> entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}

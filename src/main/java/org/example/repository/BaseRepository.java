package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.util.List;

public interface BaseRepository<T> {
    List<T> getAll() throws IOException;
    T getById(Integer id) throws IOException;
    void delete(Long id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void saveAll(List<T> tlist) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void add(T entity) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void deleteAll(Iterable<T> entities)throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}

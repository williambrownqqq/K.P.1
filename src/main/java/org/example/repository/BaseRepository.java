package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.util.List;

public interface BaseRepository<T> {
    List<T> getAll() throws IOException;
    T getById(Integer id) throws IOException;
    void delete(long id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void saveAll(List<T> tlist) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}

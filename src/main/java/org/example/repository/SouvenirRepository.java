package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;

import java.io.IOException;
import java.util.List;

public interface SouvenirRepository {
    Souvenir getById(Integer id) throws IOException;
    List<Souvenir> getAll() throws IOException;
    void add() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void update(int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void delete(int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void saveAll(List<Souvenir> souvenirs) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}

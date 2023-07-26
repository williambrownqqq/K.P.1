package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;

import java.io.IOException;
import java.util.List;

public interface MaufacturerRepository {
    Manufacturer getById(Integer id);
    List<Manufacturer> getAll();
    void add() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void update(Manufacturer user);
    void delete(int id);
}

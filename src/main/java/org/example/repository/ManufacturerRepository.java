package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ManufacturerRepository {
    Manufacturer getById(Integer id) throws IOException;
    List<Manufacturer> getAll() throws IOException;
    void add(String name, String country) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void delete(long id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void saveAll(List<Manufacturer> allManufacturers) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    List<Souvenir> deleteSouvenirsByManufacturer(List<Souvenir> souvenirs, List<Souvenir> matchingSouvenir) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException;
    List<Manufacturer> getManufacturersByPrice(double price, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) throws IOException;
    Map<String, List<Souvenir>> getAllManufacturersWithSouvenirs(List<Souvenir> souvenirs) throws IOException;
    List<Manufacturer> getManufacturersBySouvenirAndYear(String name, String year, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) throws IOException;
}

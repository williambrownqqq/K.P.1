package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Souvenir;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SouvenirRepository extends BaseRepository<Souvenir>{
    void add(String name, String manufacturer, String productionDate, double price) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    List<Souvenir> getSouvenirsByManufacturer(String manufacturer) throws IOException;
    List<Souvenir> getSouvenirsByCountry(String country) throws IOException;
    Map<String, List<Souvenir>> souvenirsByYear(List<Souvenir> souvenirs) throws IOException;
}

package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ManufacturerRepository extends BaseRepository<Manufacturer>{
//    List<Manufacturer> getManufacturersBySouvenirAndYear(String name, String year, List<Manufacturer> manufacturers, List<Souvenir> souvenirs) throws IOException;
}

package org.example.repository;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Souvenir;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SouvenirRepository extends BaseRepository<Souvenir>{
    List<Souvenir> getSouvenirsByManufacturer(String manufacturer) throws IOException;
}

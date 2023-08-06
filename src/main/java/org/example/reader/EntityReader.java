package org.example.reader;

import org.example.domain.IdentifiableEntity;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public interface EntityReader<T extends IdentifiableEntity> {
    FileReader getFileReader() throws IOException;
    List<T> readCsvFile() throws IOException;
    T getById(Long id) throws IOException;
}

package org.example.reader;

import org.example.domain.IdentifiableEntity;
import org.example.reader.impl.Reader;
import java.io.IOException;
import java.util.List;

public interface EntityReader<T extends IdentifiableEntity> extends Reader {

    List<T> readCsvFile() throws IOException;

    T getById(Long id) throws IOException;
}

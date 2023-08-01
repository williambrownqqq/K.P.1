package org.example.reader.factory;

import org.example.domain.IdentifiableEntity;
import org.example.reader.EntityReader;

public interface EntityReaderFactory {
    <T extends IdentifiableEntity> EntityReader<T> createEntityReader(String path, Class<T> clazz);
}

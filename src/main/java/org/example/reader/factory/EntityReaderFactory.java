package org.example.reader.factory;

import org.example.domain.IdentifiableEntity;
import org.example.reader.EntityReader;
import org.example.reader.impl.EntityReaderImpl;

public interface EntityReaderFactory {
    static <T extends IdentifiableEntity> EntityReader<T> createEntityReader(String path, Class<T> clazz) {
        return new EntityReaderImpl<>(path, clazz);
    }
//    <T extends IdentifiableEntity> EntityReader<T> createEntityReader(String path, Class<T> clazz);
}

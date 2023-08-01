package org.example.reader.factory.impl;

import org.example.domain.IdentifiableEntity;
import org.example.reader.EntityReader;
import org.example.reader.factory.EntityReaderFactory;
import org.example.reader.impl.EntityReaderImpl;

public class EntityReaderFactoryImpl implements EntityReaderFactory {
    @Override
    public <T extends IdentifiableEntity> EntityReader<T> createEntityReader(String path, Class<T> clazz) {
        return new EntityReaderImpl<>(path, clazz);
    }
}

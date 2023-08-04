package org.example.writer.factory;

import org.example.domain.IdentifiableEntity;
import org.example.reader.EntityReader;
import org.example.reader.impl.EntityReaderImpl;
import org.example.writer.EntityWriter;
import org.example.writer.impl.EntityWriterImpl;

public interface EntityWriterFactory {
    static<T extends IdentifiableEntity> EntityWriter<T> createEntityWriter(String path, Class<T> clazz) {
        return new EntityWriterImpl<>(path, clazz);
    }
//    <T extends IdentifiableEntity> EntityWriter<T> createEntityWriter(String path, Class<T> clazz);
}

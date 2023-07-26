package org.example.factoryWriter;

import java.io.FileWriter;
import java.io.IOException;

public interface Writer {
    FileWriter getFileWriter() throws IOException;
    FileWriter getFileWriter(boolean append) throws IOException;
}

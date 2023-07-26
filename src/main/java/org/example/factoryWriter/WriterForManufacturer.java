package org.example.factoryWriter;

import java.io.FileWriter;
import java.io.IOException;

public class WriterForManufacturer implements Writer{
    @Override
    public FileWriter getFileWriter() throws IOException {
        return new FileWriter("manufacturers.csv");
    }
    @Override
    public FileWriter getFileWriter(boolean append) throws IOException {
        return new FileWriter("manufacturers.csv", append);
    }
}

package org.example.factoryWriter;

import java.io.FileWriter;
import java.io.IOException;

public class WriterForSouvenir implements Writer{

    @Override
    public FileWriter getFileWriter() throws IOException {
        return new FileWriter("souvenirs.csv");
    }

    @Override
    public FileWriter getFileWriter(boolean append) throws IOException {
        return new FileWriter("souvenirs.csv", append);
    }
}

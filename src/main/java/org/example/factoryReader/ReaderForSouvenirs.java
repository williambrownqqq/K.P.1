package org.example.factoryReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReaderForSouvenirs implements Reader{
    @Override
    public FileReader getFileReader() throws IOException {
        return new FileReader("souvenirs.csv");
    }
}

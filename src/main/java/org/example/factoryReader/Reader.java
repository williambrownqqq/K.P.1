package org.example.factoryReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public interface Reader {
        FileReader getFileReader() throws IOException;
}
package org.example.reader.impl;

import java.io.FileReader;
import java.io.IOException;

public interface Reader {
        FileReader getFileReader() throws IOException;
}

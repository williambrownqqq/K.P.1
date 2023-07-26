package org.example.writer;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.testng.annotations.Test;

import java.io.IOException;

public class SouvenirWriterTest {

    @Test
    public void testDoWrite() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        SouvenirWriter.getInstance().doWrite();
    }
}
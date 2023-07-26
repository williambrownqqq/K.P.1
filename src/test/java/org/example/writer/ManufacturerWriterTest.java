package org.example.writer;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class ManufacturerWriterTest {

    @BeforeMethod
    public void setUp() {
    }

    @Test
    public void testWriteManufacturers() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        ManufacturerWriter.getInstance().writeManufacturers();
    }
}
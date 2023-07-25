package org.example.reader;

import org.example.domain.Manufacturer;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.testng.Assert.*;

public class ManufacturerReaderTest {

    @Test
    public void testReadCsvFile() throws FileNotFoundException {
        List<Manufacturer> manufacturerList = new ManufacturerReader().readCsvFile();
        manufacturerList.forEach(manufacturer -> System.out.println(manufacturer));
    }
}
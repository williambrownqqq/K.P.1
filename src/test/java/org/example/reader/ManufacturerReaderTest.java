//package org.example.reader;
//
//import org.example.domain.Manufacturer;
//import org.testng.annotations.Test;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
//import static org.testng.Assert.*;
//
//public class ManufacturerReaderTest {
//
//    @Test
//    public void testReadCsvFile() throws IOException {
//        List<Manufacturer> manufacturerList = ManufacturerReader.getInstance().readCsvFile();
//        manufacturerList.forEach(manufacturer -> System.out.println(manufacturer));
//    }
//}
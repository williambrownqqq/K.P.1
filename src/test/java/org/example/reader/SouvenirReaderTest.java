//package org.example.reader;
//
//import org.example.domain.Souvenir;
//import org.example.reader.impl.SouvenirReader;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.testng.Assert.*;
//
//public class SouvenirReaderTest {
//
//    @Test
//    public void testReadCsvFile() throws IOException {
//        List<Souvenir> souvenirList = SouvenirReader.getInstance().readCsvFile();
//        souvenirList.forEach(souvenir -> System.out.println(souvenir));
//    }
//}
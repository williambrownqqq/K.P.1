package org.example.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.ManufacturerReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class SouvenirServiceTest {
    SouvenirService service;
    @BeforeMethod
    public void setUp() {
        service = new SouvenirService();
    }

    @Test
    public void testAddSouvenir() {
    }

    @Test
    public void testAddManufacturer() {
    }

    @Test
    public void testUpdateSouvenir() {
    }

    @Test
    public void testUpdateManufacturer() {
    }

    @Test
    public void testViewAllSouvenirs() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> souvenirs = service.viewAllSouvenirs();
        souvenirs.forEach(System.out::println);
    }

    @Test
    public void testViewAllManufacturers() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Manufacturer> manufacturers = service.viewAllManufacturers();
        manufacturers.forEach(System.out::println);
    }
}
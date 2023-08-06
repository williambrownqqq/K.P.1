package org.example.repository.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.reader.factory.EntityReaderFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class SouvenirRepositoryImplTest {
    String manufacturerPath = "manufacturers.csv";
    String souvenirPath = "souvenirs.csv";
    private ManufacturerRepositoryImpl manufacturerRepository;
    private SouvenirRepositoryImpl souvenirRepository;
    private EntityReader<Souvenir> souvenirEntityReader;
    private EntityReader<Manufacturer> manufacturerEntityReader;

    @BeforeClass
    public void setUp() {
        manufacturerEntityReader = EntityReaderFactory.createEntityReader(manufacturerPath, Manufacturer.class);
        souvenirEntityReader = EntityReaderFactory.createEntityReader(souvenirPath, Souvenir.class);
        souvenirRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        manufacturerRepository = new ManufacturerRepositoryImpl(manufacturerPath, manufacturerEntityReader, souvenirEntityReader);
    }

    @Test
    public void testGetById() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(0L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));

        souvenirRepository.saveAll(testSouvenirs);
        Souvenir souvenir1 = souvenirRepository.getById(0);
        Souvenir souvenir2 = souvenirRepository.getById(1);

        assertEquals("Test Souvenir 1", souvenir1.getName());
        assertEquals("Test Souvenir 2", souvenir2.getName());
    }

    @Test
    public void testGetAll() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));

        souvenirRepository.saveAll(testSouvenirs);
        List<Souvenir> souvenirs = souvenirRepository.getAll();

        assertEquals(2, souvenirs.size());
        assertEquals("Test Souvenir 1", souvenirs.get(0).getName());
        assertEquals("Test Souvenir 2", souvenirs.get(1).getName());
    }

    @Test
    public void testGetSouvenirsByManufacturer() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 1", "2023-02-01", 15.0));

        souvenirRepository.saveAll(testSouvenirs);
        List<Souvenir> filteredSouvenirs = souvenirRepository.getSouvenirsByManufacturer("Manufacturer 1");

        assertEquals(2, filteredSouvenirs.size());
        assertEquals("Test Souvenir 1", filteredSouvenirs.get(0).getName() );
    }

    @Test
    public void testGetSouvenirsByCountry() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 1", "Country 1"));

        souvenirRepository.saveAll(testSouvenirs);
        manufacturerRepository.saveAll(testManufacturers);
        List<Souvenir> filteredSouvenirs = souvenirRepository.getSouvenirsByCountry("Country 1");

        assertEquals(1, filteredSouvenirs.size());
        assertEquals("Test Souvenir 1", filteredSouvenirs.get(0).getName());
    }

    @Test
    public void testAdd() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));
        souvenirRepository.saveAll(testSouvenirs);
        Souvenir souvenir = new Souvenir(3L, "New Souvenir", "New Manufacturer", "2023-08-04", 20.0);
        souvenirRepository.add(souvenir);
        testSouvenirs = souvenirRepository.getAll();

        assertEquals(3, testSouvenirs.size());
        Souvenir addedSouvenir = testSouvenirs.get(2);
        assertEquals("New Souvenir", addedSouvenir.getName());
        assertEquals("New Manufacturer", addedSouvenir.getManufacturer());
        assertEquals("2023-08-04", addedSouvenir.getProductionDate());
        assertEquals(20.0, addedSouvenir.getPrice(), 0.001); // Using delta for floating-point comparison
    }

    @Test
    public void testDelete() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));
        souvenirRepository.saveAll(testSouvenirs);

        souvenirRepository.delete(1L);
        testSouvenirs = souvenirRepository.getAll();

        assertEquals(1, testSouvenirs.size());
        Souvenir lastSouvenir = testSouvenirs.get(0);
        assertEquals("Test Souvenir 2", lastSouvenir.getName());
    }

    @Test
    public void testSaveAll() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(0L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 50.0));

        souvenirRepository.saveAll(testSouvenirs);
        List<Souvenir> getSouvenirs = souvenirRepository.getAll();
        assertEquals(2, getSouvenirs.size());
        assertEquals("Test Souvenir 1", getSouvenirs.get(0).getName());
        assertEquals("Manufacturer 1", getSouvenirs.get(0).getManufacturer());
        assertEquals("2023-01-01", getSouvenirs.get(0).getProductionDate());
        assertEquals(10.0, getSouvenirs.get(0).getPrice());
    }

    @Test
    public void testSouvenirsByYear() throws IOException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 15.0));
        testSouvenirs.add(new Souvenir(3L, "Test Souvenir 3", "Manufacturer 3", "2024-03-01", 20.0));
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 1", "Country 1"));
        Map<String, List<Souvenir>> souvenirsByYear = souvenirRepository.souvenirsByYear(testSouvenirs);

        assertTrue(souvenirsByYear.containsKey("2023-01-01"));
        assertTrue(souvenirsByYear.containsKey("2024-03-01"));

        assertEquals(2, souvenirsByYear.get("2023-01-01").size());
        assertEquals(1, souvenirsByYear.get("2024-03-01").size());
    }
}
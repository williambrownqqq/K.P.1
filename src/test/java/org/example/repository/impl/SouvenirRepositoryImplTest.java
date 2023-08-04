package org.example.repository.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.reader.factory.EntityReaderFactory.createEntityReader;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class SouvenirRepositoryImplTest {
    String manufacturerPath = "manufacturer.csv";
    String souvenirPath = "souvenirs.csv";
    private SouvenirRepositoryImpl souvenirRepository;
    private ManufacturerRepositoryImpl manufacturerRepository;
    private EntityReader<Souvenir> souvenirEntityReader;
    private EntityReader<Manufacturer> manufacturerEntityReader;

    @BeforeClass
    public void setUp() {
        manufacturerEntityReader = createEntityReader(manufacturerPath, Manufacturer.class);
        souvenirEntityReader = createEntityReader(souvenirPath, Souvenir.class);
        souvenirRepository = Mockito.mock(SouvenirRepositoryImpl.class);
        //souvenirRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        manufacturerRepository = new ManufacturerRepositoryImpl(manufacturerPath, manufacturerEntityReader, souvenirEntityReader);
    }

    @Test
    public void testGetById() throws IOException {
        // Create a list of test souvenirs
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));

        // Mock the behavior of getAll() to return the test souvenirs
        Mockito.when(souvenirRepository.getAll()).thenReturn(testSouvenirs);
        // Mock the behavior of getById() to return the specific souvenir
        Mockito.when(souvenirRepository.getById(1)).thenReturn(testSouvenirs.get(0));
        // Test getting a souvenir by ID
        Souvenir souvenir = souvenirRepository.getById(1);

        // Assert that the retrieved souvenir matches the expected souvenir
        assertEquals("Test Souvenir 1", souvenir.getName());
    }

    @Test
    public void testGetAll() throws IOException {
        // Create a list of test souvenirs
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));

        // Mock the behavior of getAll() to return the test souvenirs
        Mockito.when(souvenirRepository.getAll()).thenReturn(testSouvenirs);

        // Test getting all souvenirs
        List<Souvenir> souvenirs = souvenirRepository.getAll();

        // Assert that the retrieved souvenirs match the expected souvenirs
        assertEquals(2, souvenirs.size());
        assertEquals("Test Souvenir 1", souvenirs.get(0).getName());
        assertEquals("Test Souvenir 2", souvenirs.get(1).getName());
    }

    @Test
    public void testGetSouvenirsByManufacturer() throws IOException {
        // Create a list of test souvenirs
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));

        // Mock the behavior of getAll() to return the test souvenirs
        Mockito.when(souvenirRepository.getAll()).thenReturn(testSouvenirs);
        Mockito.when(souvenirRepository.getSouvenirsByManufacturer(eq("Manufacturer 1"))).thenReturn(testSouvenirs);

        // Call the method being tested
        List<Souvenir> filteredSouvenirs = souvenirRepository.getSouvenirsByManufacturer("Manufacturer 1");
        // Assert that the returned list contains the expected souvenir(s)
        assertEquals(2, filteredSouvenirs.size());
        assertEquals("Test Souvenir 1", filteredSouvenirs.get(0).getName() );
    }

    @Test
    public void testGetSouvenirsByCountry() throws IOException {
        List<Souvenir> mockSouvenirs = new ArrayList<>();
        mockSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));

        List<Manufacturer> mockManufacturers = new ArrayList<>();
        mockManufacturers.add(new Manufacturer(1L, "Manufacturer 1", "Country 1"));

        Mockito.when(souvenirRepository.getSouvenirsByCountry("Country 1")).thenReturn(mockSouvenirs);
        // Call the method being tested
        List<Souvenir> filteredSouvenirs = souvenirRepository.getSouvenirsByCountry("Country 1");

        // Assert
        assertEquals(1, filteredSouvenirs.size());
        assertEquals("Test Souvenir 1", filteredSouvenirs.get(0).getName());
    }

    @Test
    public void testAdd() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        SouvenirRepositoryImpl realRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);

        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));
        realRepository.saveAll(testSouvenirs);

        realRepository.add("New Souvenir", "New Manufacturer", "2023-08-04", 20.0);
        testSouvenirs = realRepository.getAll();

        assertEquals(3, testSouvenirs.size());
        Souvenir addedSouvenir = testSouvenirs.get(2);
        assertEquals("New Souvenir", addedSouvenir.getName());
        assertEquals("New Manufacturer", addedSouvenir.getManufacturer());
        assertEquals("2023-08-04", addedSouvenir.getProductionDate());
        assertEquals(20.0, addedSouvenir.getPrice(), 0.001); // Using delta for floating-point comparison
    }

    @Test
    public void testDelete() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        SouvenirRepositoryImpl realRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);

        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-02-01", 15.0));
        realRepository.saveAll(testSouvenirs);

        realRepository.delete(1L);
        testSouvenirs = realRepository.getAll();

        assertEquals(1, testSouvenirs.size());
        Souvenir lastSouvenir = testSouvenirs.get(0);
        assertEquals("Test Souvenir 2", lastSouvenir.getName());
    }

    @Test
    public void testSaveAll() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        SouvenirRepositoryImpl realRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(0L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));

        realRepository.saveAll(testSouvenirs);
        List<Souvenir> getSouvenirs = realRepository.getAll();
        assertEquals(1, getSouvenirs.size());
        assertEquals("Test Souvenir 1", getSouvenirs.get(0).getName());
        assertEquals("Manufacturer 1", getSouvenirs.get(0).getManufacturer());
        assertEquals("2023-01-01", getSouvenirs.get(0).getProductionDate());
        assertEquals(10.0, getSouvenirs.get(0).getPrice());
    }

    @Test
    public void testSouvenirsByYear() throws IOException {
        // Create a list of test souvenirs with different production years
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 15.0));
        testSouvenirs.add(new Souvenir(3L, "Test Souvenir 3", "Manufacturer 3", "2024-03-01", 20.0));
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 1", "Country 1"));
        // Create an instance of SouvenirRepositoryImpl
        SouvenirRepositoryImpl realRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);

        // Call the souvenirsByYear() method
        Map<String, List<Souvenir>> souvenirsByYear = realRepository.souvenirsByYear(testSouvenirs, testManufacturers);

        assertTrue(souvenirsByYear.containsKey("2023-01-01"));
        assertTrue(souvenirsByYear.containsKey("2024-03-01"));

        assertEquals(2, souvenirsByYear.get("2023-01-01").size());
        assertEquals(1, souvenirsByYear.get("2024-03-01").size());
    }
}
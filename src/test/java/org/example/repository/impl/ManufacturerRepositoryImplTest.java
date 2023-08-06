package org.example.repository.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.example.reader.factory.EntityReaderFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

public class ManufacturerRepositoryImplTest {
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
    public void testGetById() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(0L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 2", "Country 2"));

        manufacturerRepository.saveAll(testManufacturers);
        Manufacturer manufacturer1 = manufacturerRepository.getById(0);
        Manufacturer manufacturer2 = manufacturerRepository.getById(1);

        assertEquals("Manufacturer 1", manufacturer1.getName());
        assertEquals("Manufacturer 2", manufacturer2.getName());
    }

    @Test
    public void testGetAll() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(0L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 2", "Country 2"));

        manufacturerRepository.saveAll(testManufacturers);
        List<Manufacturer> manufacturers = manufacturerRepository.getAll();

        assertEquals(2, manufacturers.size());
        assertEquals("Manufacturer 1", manufacturers.get(0).getName());
        assertEquals("Manufacturer 2", manufacturers.get(1).getName());
    }

    @Test
    public void testAdd() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(2L, "Manufacturer 2", "Country 2"));
        manufacturerRepository.saveAll(testManufacturers);

        Manufacturer manufacturer = Manufacturer.builder()
                .name("Manufacturer 3")
                .country("Country 3")
                .build();
        manufacturerRepository.add(manufacturer);
        testManufacturers = manufacturerRepository.getAll();

        assertEquals(3, testManufacturers.size());
        Manufacturer addedManufacturer = testManufacturers.get(2);
        assertEquals(Long.valueOf(3L), addedManufacturer.getId());
        assertEquals("Manufacturer 3", addedManufacturer.getName());
        assertEquals("Country 3", addedManufacturer.getCountry());
    }

    @Test
    public void testDelete() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(0L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 2", "Country 2"));
        manufacturerRepository.saveAll(testManufacturers);

        manufacturerRepository.delete(0L);
        testManufacturers = manufacturerRepository.getAll();

        assertEquals(1, testManufacturers.size());
        Manufacturer lastManufacturer = testManufacturers.get(0);
        assertEquals("Manufacturer 2", lastManufacturer.getName());
    }

    @Test
    public void testSaveAll() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(0L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 2", "Country 2"));
        manufacturerRepository.saveAll(testManufacturers);

        List<Manufacturer> getManufacturers = manufacturerRepository.getAll();
        assertEquals(2, getManufacturers.size());
        assertEquals("Manufacturer 1", getManufacturers.get(0).getName());
        assertEquals("Country 1", getManufacturers.get(0).getCountry());
    }

    @Test
    public void testDeleteSouvenirsByManufacturer() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 15.0));
        testSouvenirs.add(new Souvenir(3L, "Test Souvenir 3", "Manufacturer 2", "2024-03-01", 20.0));
        souvenirRepository.saveAll(testSouvenirs);
        List<Souvenir> checkSouvenirs = manufacturerRepository.deleteSouvenirsByManufacturer(testSouvenirs, List.of(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 15.0)));

        assertEquals(2, checkSouvenirs.size());
        assertEquals("Test Souvenir 1", checkSouvenirs.get(0).getName());
        assertEquals("Test Souvenir 3", checkSouvenirs.get(1).getName());
    }

    @Test
    public void testGetManufacturersByPrice() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(0L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 2", "Country 2"));
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 15.0));
        testSouvenirs.add(new Souvenir(3L, "Test Souvenir 3", "Manufacturer 2", "2024-03-01", 20.0));
        manufacturerRepository.saveAll(testManufacturers);
        souvenirRepository.saveAll(testSouvenirs);

        List<Manufacturer> getManufacturersByPrice = manufacturerRepository.getManufacturersByPrice(16L, testManufacturers, testSouvenirs);
        assertEquals(2, getManufacturersByPrice.size());

        assertEquals("Manufacturer 1",  getManufacturersByPrice.get(0).getName());
        assertEquals("Country 1",  getManufacturersByPrice.get(0).getCountry());

        assertEquals("Manufacturer 2",  getManufacturersByPrice.get(1).getName());
        assertEquals("Country 2",  getManufacturersByPrice.get(1).getCountry());
    }

    @Test
    public void testGetManufacturersBySouvenirAndYear() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<Manufacturer> testManufacturers = new ArrayList<>();
        testManufacturers.add(new Manufacturer(0L, "Manufacturer 1", "Country 1"));
        testManufacturers.add(new Manufacturer(1L, "Manufacturer 2", "Country 2"));
        List<Souvenir> testSouvenirs = new ArrayList<>();
        testSouvenirs.add(new Souvenir(1L, "Test Souvenir 1", "Manufacturer 1", "2023-01-01", 10.0));
        testSouvenirs.add(new Souvenir(2L, "Test Souvenir 2", "Manufacturer 2", "2023-01-01", 15.0));
        testSouvenirs.add(new Souvenir(3L, "Test Souvenir 3", "Manufacturer 2", "2024-03-01", 20.0));
        manufacturerRepository.saveAll(testManufacturers);
        souvenirRepository.saveAll(testSouvenirs);

        List<Manufacturer> getManufacturers = manufacturerRepository.getManufacturersBySouvenirAndYear("Test Souvenir 1","2023-01-01",testManufacturers, testSouvenirs);
        assertEquals(1, getManufacturers.size());
        assertEquals("Manufacturer 1", getManufacturers.get(0).getName());
        assertEquals("Country 1", getManufacturers.get(0).getCountry());
    }
}
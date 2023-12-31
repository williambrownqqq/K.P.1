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

}
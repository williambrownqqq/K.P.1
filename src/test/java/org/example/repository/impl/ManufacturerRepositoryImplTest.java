package org.example.repository.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

public class ManufacturerRepositoryImplTest {

    @Test
    public void testGetById() throws IOException {
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl();
        repository.getById(0);

    }

    @Test
    public void testGetAll() throws IOException {
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl();
        List<Manufacturer> manufacturers = repository.getAll();
        manufacturers.forEach(System.out::println);
    }

    @Test
    public void testAdd() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl();
        repository.add();
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testDelete() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        ManufacturerRepositoryImpl repository = new ManufacturerRepositoryImpl();
        repository.delete(1);

    }
}
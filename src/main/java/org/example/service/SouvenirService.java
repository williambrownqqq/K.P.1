package org.example.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.ManufacturerReader;
import org.example.reader.SouvenirReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SouvenirService {
    public static int IDcounterManufacturer = 2;
    public static int IDcounterSouvenir = 2;

    private List<Souvenir> souvenirs;
    private List<Manufacturer> manufacturers;

    public SouvenirService() {
        this.souvenirs = new ArrayList<>();
        this.manufacturers = new ArrayList<>();
    }

    // Add
    public void addSouvenir(Souvenir souvenir) {
        souvenirs.add(souvenir);
    }

    public void addManufacturer(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    // Update
    public void updateSouvenir(Souvenir souvenir) {
        souvenirs.add(souvenir);
    }

    public void updateManufacturer(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    public List<Souvenir> viewAllSouvenirs() throws IOException {
        List<Souvenir> souvenirs = SouvenirReader.getInstance().readCsvFile();
        return souvenirs;
    }

    public List<Manufacturer> viewAllManufacturers() throws IOException {
        List<Manufacturer> manufacturers = ManufacturerReader.getInstance().readCsvFile();
        return manufacturers;
    }


}

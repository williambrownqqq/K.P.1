package org.example;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.service.SouvenirService;
import org.example.writer.SouvenirWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
//        SouvenirService service = new SouvenirService();
//
//        // add some manufacturers and Souvenirs
//        Manufacturer manufacturer1 = new Manufacturer("Manufacturer 1", "South Korea");
//        Manufacturer manufacturer2 = new Manufacturer("Manufacturer 2", "The United States");
//        service.addManufacturer(manufacturer1);
//        service.addManufacturer(manufacturer2);
//
//        Souvenir souvenir1 = new Souvenir("Souvenir 1", "Manufacturer 1", "2021", 10.0);
//        Souvenir souvenir2 = new Souvenir("Souvenir 2", "Manufacturer 2", "2022", 15.0);
//        Souvenir souvenir3 = new Souvenir("Souvenir 3", "Manufacturer 2", "2023", 20.0);
//        service.addSouvenir(souvenir1);
//        service.addSouvenir(souvenir2);
//        service.addSouvenir(souvenir3);

        // check it our file system them

        SouvenirWriter writer = new SouvenirWriter();
        writer.doWrite();
    }
}
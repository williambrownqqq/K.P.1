package org.example;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.reader.factory.EntityReaderFactory;
import org.example.reader.factory.impl.EntityReaderFactoryImpl;
import org.example.repository.ManufacturerRepository;
import org.example.repository.SouvenirRepository;
import org.example.repository.impl.ManufacturerRepositoryImpl;
import org.example.repository.impl.SouvenirRepositoryImpl;
import org.example.service.ManufacturerService;
import org.example.service.SouvenirService;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String souvenirPath = args[0];
        String manufacturerPath = args[1];
        // get readers for each entity

//        EntityReaderImpl<Souvenir> souvenirEntityReader = new EntityReaderImpl<>(souvenirPath, Souvenir.class);
//        EntityReaderImpl<Manufacturer> manufacturerEntityReader = new EntityReaderImpl<>(manufacturerPath, Manufacturer.class);

        // get readers by factory
        EntityReaderFactory entityReaderFactory = new EntityReaderFactoryImpl();
        EntityReader<Manufacturer> manufacturerEntityReader = entityReaderFactory.createEntityReader(manufacturerPath, Manufacturer.class);
        EntityReader<Souvenir> souvenirEntityReader = entityReaderFactory.createEntityReader(souvenirPath, Souvenir.class);

        List<Manufacturer> manufacturers = manufacturerEntityReader.readCsvFile();
        manufacturers.forEach(System.out::println);

        List<Souvenir> souvenirs = souvenirEntityReader.readCsvFile();
        souvenirs.forEach(System.out::println);

//        ManufacturerReader manufacturerReader = new ManufacturerReader(manufacturerPath);
//        SouvenirReader souvenirReader = new SouvenirReader(souvenirPath);
//
        SouvenirRepository souvenirRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        ManufacturerRepository manufacturerRepository = new ManufacturerRepositoryImpl(manufacturerPath, manufacturerEntityReader,souvenirEntityReader);

        SouvenirService souvenirService = new SouvenirService(souvenirRepository, manufacturerRepository,souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        ManufacturerService manufacturerService = new ManufacturerService(souvenirRepository, manufacturerRepository, manufacturerPath, souvenirEntityReader, manufacturerEntityReader);

        // get
        List<Souvenir> souvenirsList = souvenirService.viewAllSouvenirs();
        souvenirsList.stream().forEach(System.out::println);

        List<Manufacturer> manufacturersList = manufacturerService.viewAllManufacturers();
        manufacturersList.stream().forEach(System.out::println);

        // delete
        //ouvenirService.deleteSouvenir();

        // update
//        souvenirService.updateSouvenir();
        // add
//        souvenirService.addSouvenir();

        // 1
//        List<Souvenir> souvenirsByManufacturer = souvenirService.getSouvenirsByManufacturer();
//        souvenirsByManufacturer.stream().forEach(System.out::println);

        // 2
//        List<Souvenir> souvenirsByCountry = souvenirService.getSouvenirsByCountry();
//        souvenirsByCountry.stream().forEach(System.out::println);

        // 3
//        List<Manufacturer> manufacturersByPrice = manufacturerService.getManufacturersByPrice();
//        manufacturersByPrice.stream().forEach(System.out::println);
        // 4
//        manufacturerService.printAllManufacturersWithSouvenirs();
        // 5
//        List<Manufacturer> manufacturersBySouvenirAndYear = manufacturerService.getManufacturersBySouvenirAndYear();
//        manufacturersBySouvenirAndYear.stream().forEach(System.out::println);
//
        // 7
//        souvenirService.printSouvenirsWithYear();

        // 8
        //souvenirService.deleteSouvenirsByManufacturer();


    }

}
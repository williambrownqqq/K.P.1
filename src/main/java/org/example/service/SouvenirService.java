package org.example.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.repository.ManufacturerRepository;
import org.example.repository.SouvenirRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SouvenirService {
    private final String souvenirPath;
    private final SouvenirRepository souvenirRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final EntityReader<Souvenir> souvenirEntityReader;
    private final EntityReader<Manufacturer> manufacturerEntityReader;

    public SouvenirService(SouvenirRepository souvenirRepository, ManufacturerRepository manufacturerRepository, String souvenirPath, EntityReader<Souvenir> souvenirEntityReader, EntityReader<Manufacturer> manufacturerEntityReader) {
        this.souvenirRepository = souvenirRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.souvenirPath = souvenirPath;
        this.souvenirEntityReader = souvenirEntityReader;
        this.manufacturerEntityReader = manufacturerEntityReader;
    }

    public List<Souvenir> getAll() throws IOException {
        List<Souvenir> souvenirList = souvenirEntityReader.readCsvFile();
        return souvenirList;
    }

    // Add
    public void addSouvenir() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Enter productionDate: ");
        String productionDate = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        souvenirRepository.add(name, manufacturer, productionDate, price);
    }

    // Update
    public void updateSouvenir() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Souvenir name : ");
        String souvenirName = scanner.nextLine();

        List<Souvenir> souvenirs = souvenirRepository.getAll();
       long id = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .mapToLong(Souvenir::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(-1L);
        souvenirRepository.delete(id);

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Enter new productionDate: ");
        String productionDate = scanner.nextLine();

        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        souvenirs = souvenirRepository.getAll();
        souvenirs.add((int) id, new Souvenir(id, name, manufacturer, productionDate, price));
        souvenirRepository.saveAll(souvenirs);

    }
    public void deleteSouvenir() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Souvenir name : ");
        String souvenirName = scanner.nextLine();

        List<Souvenir> souvenirs = souvenirRepository.getAll();
        long id = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .mapToLong(Souvenir::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(-1L);
        souvenirRepository.delete(id);
    }

    public List<Souvenir> viewAllSouvenirs() throws IOException {
        return souvenirRepository.getAll();
    }

    public void deleteSouvenirsByManufacturer() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter manufacturer name : ");
        String manufacturerName = scanner.nextLine();

        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        List<Souvenir> souvenirs = souvenirRepository.getAll();
        List<Souvenir> matchingSouvenirs = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getManufacturer().equalsIgnoreCase(manufacturerName))
                .toList();
        List<Souvenir> souvenirList = manufacturerRepository.deleteSouvenirsByManufacturer(souvenirs, matchingSouvenirs);
        souvenirRepository.saveAll(souvenirList);

    }

    public List<Souvenir> getSouvenirsByManufacturer() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();
        return souvenirRepository.getSouvenirsByManufacturer(manufacturer);
    }

    public List<Souvenir> getSouvenirsByCountry() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter сountry: ");
        String country = scanner.nextLine();
        return souvenirRepository.getSouvenirsByCountry(country);
    }
    public Map<String, List<Souvenir>> souvenirsByYear() throws IOException {
        List<Souvenir> souvenirs = souvenirRepository.getAll();
        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        return souvenirRepository.souvenirsByYear(souvenirs, manufacturers);
    }
    public void printSouvenirsWithYear() throws IOException {
        Map<String, List<Souvenir>> souvenirsByYear = souvenirsByYear();

        for (Map.Entry<String, List<Souvenir>> entry : souvenirsByYear.entrySet()) {
            String souvenirName = entry.getKey();
            List<Souvenir> souvenirs = entry.getValue();

            System.out.println("Year: " + souvenirName);
            System.out.println("Souvenirs: ");
            for (Souvenir souvenir : souvenirs) {
                System.out.println("- " + souvenir.getName() + " (" + souvenir.getPrice() + ")");
            }
            System.out.println(); // Add a blank line between each manufacturer's souvenirs
        }

    }
}

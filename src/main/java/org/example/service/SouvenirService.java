package org.example.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.repository.ManufacturerRepository;
import org.example.repository.SouvenirRepository;
import org.example.repository.impl.ManufacturerRepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SouvenirService {
    private final SouvenirRepository souvenirRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final EntityReader<Souvenir> souvenirEntityReader;

    public SouvenirService(SouvenirRepository souvenirRepository, ManufacturerRepository manufacturerRepository, EntityReader<Souvenir> souvenirEntityReader) {
        this.souvenirRepository = souvenirRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.souvenirEntityReader = souvenirEntityReader;
    }

    public List<Souvenir> getAll() throws IOException {
        return souvenirEntityReader.readCsvFile();
    }

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

        Souvenir souvenir = Souvenir.builder()
                .name(name)
                .manufacturer(manufacturer)
                .productionDate(productionDate)
                .price(price)
                .build();
        souvenirRepository.add(souvenir);
    }
    public void updateSouvenir() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Souvenir name : ");
        String souvenirName = scanner.nextLine();
        List<Souvenir> souvenirs = souvenirRepository.getAll();

        Long id = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .map(Souvenir::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(null);
        souvenirRepository.delete(id);

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Enter new productionDate: ");
        String productionDate = scanner.nextLine();

        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        souvenirRepository.add(new Souvenir(id, name, manufacturer, productionDate, price));
    }
    public void deleteSouvenir() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Souvenir name : ");
        String souvenirName = scanner.nextLine();

        List<Souvenir> souvenirs = souvenirRepository.getAll();
        Long id = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .map(Souvenir::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(null);

        souvenirRepository.delete(id);
    }
    public List<Souvenir> viewAllSouvenirs() throws IOException {
        return souvenirRepository.getAll();
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

        List<Souvenir> souvenirs = souvenirRepository.getAll();
        List<Manufacturer> manufacturers = manufacturerRepository.getAll();

        Set<String> filteredManufacturers = manufacturers
                .stream()
                .filter(manufacturer -> manufacturer.getCountry().equalsIgnoreCase(country))
                .map(Manufacturer::getName)
                .collect(Collectors.toSet());
        return souvenirs.stream()
                .filter(souvenir -> filteredManufacturers.contains(souvenir.getManufacturer()))
                .toList();
    }
    public Map<String, List<Souvenir>> souvenirsByYear() throws IOException {
        return souvenirRepository.getAll()
                .stream()
                .collect(Collectors.groupingBy(Souvenir::getProductionDate));
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

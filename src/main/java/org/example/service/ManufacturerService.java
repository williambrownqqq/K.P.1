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
import java.util.stream.Collectors;

public class ManufacturerService {
    private final String manufacturerPath;
    private final SouvenirRepository souvenirRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final EntityReader<Souvenir> souvenirEntityReader;
    private final EntityReader<Manufacturer> manufacturerEntityReader;

    public ManufacturerService(SouvenirRepository souvenirRepository, ManufacturerRepository manufacturerRepository, String manufacturerPath, EntityReader<Souvenir> souvenirEntityReader, EntityReader<Manufacturer> manufacturerEntityReader) {
        this.manufacturerPath = manufacturerPath;
        this.souvenirRepository = souvenirRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.souvenirEntityReader = souvenirEntityReader;
        this.manufacturerEntityReader = manufacturerEntityReader;
    }

    public List<Manufacturer> getAll() throws IOException {
        List<Manufacturer> manufacturers = manufacturerEntityReader.readCsvFile();
        return manufacturers;
    }

    public List<Manufacturer> viewAllManufacturers() throws IOException {
        return manufacturerRepository.getAll();
    }
    public void addManufacturer() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter country: ");
        String country = scanner.nextLine();


        manufacturerRepository.add(name, country);
    }
    public void updateManufacturer() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Manufacturer name : ");
        String manufacturerName = scanner.nextLine();

        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
//        List<Manufacturer> filteredManufacturers = manufacturers.stream()
//                .filter(souvenir -> souvenir.getName().equalsIgnoreCase(manufacturerName))
//                .toList();
        long id = manufacturers
                .stream()
                .filter(manufacturer -> manufacturer.getName().equalsIgnoreCase(manufacturerName))
                .mapToLong(Manufacturer::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(-1L);

        manufacturerRepository.delete(id);

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Country: ");
        String country = scanner.nextLine();

        manufacturers = manufacturerRepository.getAll();
        manufacturers.add((int)id, new Manufacturer(id, name, country));
        manufacturerRepository.saveAll(manufacturers);

    }
    public void deleteManufacturer() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Manufacturer name : ");
        String manufacturerName = scanner.nextLine();

        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        long id = manufacturers
                .stream()
                .filter(manufacturer -> manufacturer.getName().equals(manufacturerName))
                .mapToLong(Manufacturer::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(-1L);
        manufacturerRepository.delete(id);
    }
    public Manufacturer getManufacturerById(Integer id) throws IOException {
        return manufacturerRepository.getById(id);
    }
    public List<Manufacturer> getManufacturersByPrice() throws IOException {
        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        List<Souvenir> souvenirs = souvenirRepository.getAll();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        return manufacturerRepository.getManufacturersByPrice(price, manufacturers, souvenirs);
    }
    public Map<String, List<Souvenir>> getAllManufacturersWithSouvenirs() throws IOException {
        List<Souvenir> souvenirs = souvenirRepository.getAll();
        return manufacturerRepository.getAllManufacturersWithSouvenirs(souvenirs);
    }
    public void printAllManufacturersWithSouvenirs() throws IOException {
        Map<String, List<Souvenir>> manufacturersWithSouvenirs = getAllManufacturersWithSouvenirs();

        for (Map.Entry<String, List<Souvenir>> entry : manufacturersWithSouvenirs.entrySet()) {
            String manufacturerName = entry.getKey();
            List<Souvenir> souvenirs = entry.getValue();

            System.out.println("Manufacturer: " + manufacturerName);
            System.out.println("Souvenirs: ");
            for (Souvenir souvenir : souvenirs) {
                System.out.println("- " + souvenir.getName() + " (" + souvenir.getPrice() + ")");
            }
            System.out.println(); // Add a blank line between each manufacturer's souvenirs
        }
    }
    public List<Manufacturer> getManufacturersBySouvenirAndYear() throws IOException {
        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        List<Souvenir> souvenirs =souvenirRepository.getAll();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter souvenir name: ");
        String name = scanner.nextLine();

        System.out.print("Enter year: ");
        String productionDate = scanner.nextLine();

        return manufacturerRepository.getManufacturersBySouvenirAndYear(name, productionDate, manufacturers, souvenirs);
    }

}

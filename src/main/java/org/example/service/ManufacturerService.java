package org.example.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.repository.ManufacturerRepository;
import org.example.repository.SouvenirRepository;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ManufacturerService {

    private final SouvenirRepository souvenirRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final EntityReader<Manufacturer> manufacturerEntityReader;

    public ManufacturerService(SouvenirRepository souvenirRepository, ManufacturerRepository manufacturerRepository, EntityReader<Manufacturer> manufacturerEntityReader) {
        this.souvenirRepository = souvenirRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerEntityReader = manufacturerEntityReader;
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

        Manufacturer manufacturer = Manufacturer.builder()
                .name(name)
                .country(country)
                .build();

        manufacturerRepository.add(manufacturer);
    }
    public void updateManufacturer() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Manufacturer name : ");
        String manufacturerName = scanner.nextLine();

        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        Long id = manufacturers
                .stream()
                .filter(manufacturer -> manufacturer.getName().equalsIgnoreCase(manufacturerName))
                .map(Manufacturer::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(null);

        manufacturerRepository.delete(id);

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Country: ");
        String country = scanner.nextLine();

        manufacturers = manufacturerRepository.getAll();
        manufacturers.add(id.intValue()-1, new Manufacturer(id, name, country));
        manufacturerRepository.saveAll(manufacturers);

    }
    public void deleteManufacturer() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Manufacturer name : ");
        String manufacturerName = scanner.nextLine();

        List<Manufacturer> manufacturers = manufacturerRepository.getAll();
        Long id = manufacturers
                .stream()
                .filter(manufacturer -> manufacturer.getName().equalsIgnoreCase(manufacturerName))
                .map(Manufacturer::getId) // Assuming there's a method getId() to get the ID of the Souvenir
                .findFirst() // Take the first matching souvenir's ID
                .orElse(null);
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

        Set<String> distinctFilteredManufacturersNames = souvenirs
                .stream()
                .filter(souvenir -> souvenir.getPrice() < price)
                .map(Souvenir::getManufacturer)
                .collect(Collectors.toSet());

        return manufacturers
                .stream()
                .filter(manufacturer -> distinctFilteredManufacturersNames.contains(manufacturer.getName()))
                .collect(Collectors.toList());
    }
    public Map<String, List<Souvenir>> getAllManufacturersWithSouvenirs() throws IOException {
        return souvenirRepository.getAll().stream()
                .collect(Collectors.groupingBy(Souvenir::getManufacturer));
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

        return souvenirs.stream()
                .filter(souvenir -> souvenir.getName().equalsIgnoreCase(name) &&
                        souvenir.getProductionDate().equalsIgnoreCase(productionDate))
                .map(Souvenir::getManufacturer)
                .distinct() // Remove duplicate manufacturer names
                .flatMap(manufacturerName ->
                        manufacturers.stream()
                                .filter(manufacturer -> manufacturer.getName().equalsIgnoreCase(manufacturerName))
                )
                .collect(Collectors.toList());
        //return manufacturerRepository.getManufacturersBySouvenirAndYear(name, productionDate, manufacturers, souvenirs);
    }

    public void deleteSouvenirsByManufacturer() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter manufacturer name : ");
        String manufacturerName = scanner.nextLine();

        Manufacturer manufacturer = manufacturerRepository.getAll().stream()
                .filter(e -> manufacturerName.equalsIgnoreCase(e.getName()))
                .findFirst()
                .orElse(null);
        if(manufacturer == null) return;
        List<Souvenir> matchingSouvenirs = souvenirRepository.getAll()
                .stream()
                .filter(souvenir -> souvenir.getManufacturer().equalsIgnoreCase(manufacturerName))
                .toList();
        souvenirRepository.deleteAll(matchingSouvenirs);
        manufacturerRepository.delete(manufacturer.getId());
    }

}

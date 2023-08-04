package org.example;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.example.domain.Manufacturer;
import org.example.domain.Souvenir;
import org.example.reader.EntityReader;
import org.example.reader.factory.EntityReaderFactory;
import org.example.reader.factory.impl.EntityReaderFactoryImpl;
import org.example.reader.impl.EntityReaderImpl;
import org.example.repository.ManufacturerRepository;
import org.example.repository.SouvenirRepository;
import org.example.repository.impl.ManufacturerRepositoryImpl;
import org.example.repository.impl.SouvenirRepositoryImpl;
import org.example.service.ManufacturerService;
import org.example.service.SouvenirService;
import org.example.writer.EntityWriter;
import org.example.writer.factory.EntityWriterFactory;
import org.example.writer.factory.impl.EntityWriterFactoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.reader.factory.EntityReaderFactory.createEntityReader;
import static org.example.writer.factory.EntityWriterFactory.createEntityWriter;


public class Main {
    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String souvenirPath = args[0];
        String manufacturerPath = args[1];
        // load data to correct work with app
        loadData(souvenirPath, manufacturerPath);

//         get readers for each entity
//        EntityReaderImpl<Souvenir> souvenirEntityReader = new EntityReaderImpl<>(souvenirPath, Souvenir.class);
//        EntityReaderImpl<Manufacturer> manufacturerEntityReader = new EntityReaderImpl<>(manufacturerPath, Manufacturer.class);

         // get readers by factory
        //EntityReaderFactory entityReaderFactory = new EntityReaderFactoryImpl();
        EntityReader<Manufacturer> manufacturerEntityReader = createEntityReader(manufacturerPath, Manufacturer.class);
        EntityReader<Souvenir> souvenirEntityReader = createEntityReader(souvenirPath, Souvenir.class);

        // get repositories
        SouvenirRepository souvenirRepository = new SouvenirRepositoryImpl(souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        ManufacturerRepository manufacturerRepository = new ManufacturerRepositoryImpl(manufacturerPath, manufacturerEntityReader,souvenirEntityReader);
        // get services
        SouvenirService souvenirService = new SouvenirService(souvenirRepository, manufacturerRepository,souvenirPath, souvenirEntityReader, manufacturerEntityReader);
        ManufacturerService manufacturerService = new ManufacturerService(souvenirRepository, manufacturerRepository, manufacturerPath, souvenirEntityReader, manufacturerEntityReader);


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

        Scanner scanner = new Scanner(System.in);
        boolean continueExecution = true;

        while (continueExecution) {
            System.out.println("Choose an option:");
            System.out.println("1. Переглянути всі записи.");
            System.out.println("2. Вивести інформацію про сувеніри заданого виробника.");
            System.out.println("3. Вивести інформацію про сувеніри, виготовлені в заданій країні.");
            System.out.println("4. Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.");
            System.out.println("5. Вивести інформацію по всіх виробниках та, для кожного виробника вивести інформацію\n" +
                    "про всі сувеніри, які він виробляє.");
            System.out.println("6. Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року.");
            System.out.println("7. Для кожного року вивести список сувенірів, зроблених цього року.");
            System.out.println("8. Видалити заданого виробника та його сувеніри.");
            System.out.println("9. Додати сувенір.");
            System.out.println("10. Додати виробника.");
            System.out.println("11. Обновити сувенір.");
            System.out.println("12. Обновити виробника.");
            System.out.println("13. Видалити сувенір.");
            System.out.println("14. Видалити виробника.");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("  Souveirs:  ");
                    List<Souvenir> souvenirsList = souvenirService.viewAllSouvenirs();
                    souvenirsList.stream().forEach(System.out::println);

                    System.out.println("  Manufacturers:  ");
                    List<Manufacturer> manufacturersList = manufacturerService.viewAllManufacturers();
                    manufacturersList.stream().forEach(System.out::println);
                    break;
                case 2:
                    List<Souvenir> souvenirsByManufacturer = souvenirService.getSouvenirsByManufacturer();
                    souvenirsByManufacturer.stream().forEach(System.out::println);
                    break;
                case 3:
                    List<Souvenir> souvenirsByCountry = souvenirService.getSouvenirsByCountry();
                    souvenirsByCountry.stream().forEach(System.out::println);
                    break;
                case 4:
                    List<Manufacturer> manufacturersByPrice = manufacturerService.getManufacturersByPrice();
                    manufacturersByPrice.stream().forEach(System.out::println);
                case 5:
                    manufacturerService.printAllManufacturersWithSouvenirs();
                    break;
                case 6:
                    List<Manufacturer> manufacturersBySouvenirAndYear = manufacturerService.getManufacturersBySouvenirAndYear();
                    manufacturersBySouvenirAndYear.stream().forEach(System.out::println);
                    break;
                case 7:
                    souvenirService.printSouvenirsWithYear();
                    break;
                case 8:
                    souvenirService.deleteSouvenirsByManufacturer();
                    break;
                case 9:
                    souvenirService.addSouvenir();;
                    break;
                case 10:
                    manufacturerService.addManufacturer();
                    break;
                case 11:
                    souvenirService.updateSouvenir();
                    break;
                case 12:
                    manufacturerService.updateManufacturer();
                    break;
                case 13:
                    souvenirService.deleteSouvenir();
                    break;
                case 14:
                    manufacturerService.deleteManufacturer();
                    break;
                case 0:
                    continueExecution = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            if (continueExecution) {
                System.out.println("\nDo you want to continue? (y/n):");
                String answer = scanner.next().trim().toLowerCase();
                continueExecution = answer.equals("y") || answer.equals("yes");
            }
        }
        scanner.close();
    }

    public static void loadData(String souvenirPath, String manufacturerPath) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        loadManufacturer(manufacturerPath);
        loadSouvenir(souvenirPath);
    }

    public static void loadManufacturer(String manufacturerPath) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        EntityWriterFactory entityWriterFactory = new EntityWriterFactoryImpl();
        EntityWriter<Manufacturer> manufacturerEntityWriter = createEntityWriter(manufacturerPath, Manufacturer.class);
        Manufacturer.ManufacturerBuilder builder = new Manufacturer.ManufacturerBuilder();
        Manufacturer manufacturer4 =  builder.id(4L)
                .name("Apple")
                .country("USA")
                .build();
        builder = new Manufacturer.ManufacturerBuilder();
        Manufacturer manufacturer1 =  builder.id(1L)
                .name("Samsung")
                .country("South Korea")
                .build();
        Manufacturer manufacturer3 =  builder.id(3L)
                .name("Ford")
                .country("USA")
                .build();
        Manufacturer manufacturer2 =  builder.id(2L)
                .name("Antonov")
                .country("UA")
                .build();
        List<Manufacturer> manufacturerList = List.of(
                manufacturer1,
                manufacturer2,
                manufacturer3,
                manufacturer4
        );
        manufacturerEntityWriter.override(manufacturerList);
    }

    public static void loadSouvenir(String souvenirPath) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        EntityWriterFactory entityWriterFactory = new EntityWriterFactoryImpl();
        EntityWriter<Souvenir> souvenirEntityWriter = createEntityWriter(souvenirPath, Souvenir.class);
        Souvenir.SouvenirBuilder builder = new Souvenir.SouvenirBuilder();

        Souvenir souvenir1 = builder.id(0L)
                .name("phone")
                .manufacturer("Apple")
                .productionDate("2023")
                .price(1000)
                .build();
        Souvenir souvenir2 = builder.id(1L)
                .name("car")
                .manufacturer("Ford")
                .productionDate("2022")
                .price(5000)
                .build();
        Souvenir souvenir3 = builder.id(2L)
                .name("plane")
                .manufacturer("Antonov")
                .productionDate("2021")
                .price(9999)
                .build();
        Souvenir souvenir4 = builder.id(2L)
                .name("phone")
                .manufacturer("Apple")
                .productionDate("2020")
                .price(700)
                .build();
        Souvenir souvenir5 = builder.id(2L)
                .name("phone")
                .manufacturer("Apple")
                .productionDate("2021")
                .price(800)
                .build();
        Souvenir souvenir6 = builder.id(5L)
                .name("laptop")
                .manufacturer("Dell")
                .productionDate("2002")
                .price(300)
                .build();
        Souvenir souvenir7 = builder.id(6L)
                .name("himars")
                .manufacturer("Lockhead Martin")
                .productionDate("2005")
                .price(999999)
                .build();

        List<Souvenir> souvenirList = List.of(
                souvenir1,
                souvenir2,
                souvenir3,
                souvenir4,
                souvenir5,
                souvenir6,
                souvenir7
        );
        souvenirEntityWriter.override(souvenirList);
    }
}
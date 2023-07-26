package org.example.factoryWriter;

public class WriterFactory {
    public Writer createWrite(String writePath) {
        if (writePath == null) {
            return null;
        } else if (writePath.equalsIgnoreCase("manufacturers.csv")) {
            return new WriterForManufacturer();
        } else if (writePath.equalsIgnoreCase("souvenirs.csv")) {
            return new WriterForSouvenir();
        }

        return null;
    }
}

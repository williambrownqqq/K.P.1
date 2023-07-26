package org.example.factoryReader;

import java.io.FileWriter;
import java.io.IOException;

public class ReaderFactory{
    public Reader createReader(String path){
        if(path == null){
            return null;
        }
        else if(path.equalsIgnoreCase("manufacturers.csv")){
            return new ReaderForManufacturer();
        }
        else if(path.equalsIgnoreCase("souvenirs.csv")){
            return new ReaderForSouvenirs();
        }
        else return null;

    }
}

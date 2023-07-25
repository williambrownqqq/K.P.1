package org.example.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Souvenir {
    @CsvBindByPosition(position = 0)
    private Integer id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String manufacturer;
    @CsvBindByPosition(position = 3)
    private String productionDate;
    @CsvBindByPosition(position = 4)
    private double price;

}

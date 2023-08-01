package org.example.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Souvenir implements IdentifiableEntity{
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String manufacturer;
    @CsvBindByPosition(position = 3)
    private String productionDate;
    @CsvBindByPosition(position = 4)
    private double price;

    public static SouvenirBuilder builder() {
        return new SouvenirBuilder();
    }

    public static class SouvenirBuilder {
        private Long id;
        private String name;
        private String manufacturer;
        private String productionDate;
        private double price;

        public SouvenirBuilder() {}

        public SouvenirBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SouvenirBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SouvenirBuilder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public SouvenirBuilder productionDate(String productionDate) {
            this.productionDate = productionDate;
            return this;
        }

        public SouvenirBuilder price(double price) {
            this.price = price;
            return this;
        }

        public Souvenir build() {
            return new Souvenir(id, name, manufacturer, productionDate, price);
        }
    }

}

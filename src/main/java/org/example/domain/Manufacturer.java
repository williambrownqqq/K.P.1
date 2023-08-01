package org.example.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer implements IdentifiableEntity{
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String country;

    public static ManufacturerBuilder builder() {
        return new ManufacturerBuilder();
    }

    public static class ManufacturerBuilder {
        private Long id;
        private String name;
        private String country;

        public ManufacturerBuilder() {}

        public ManufacturerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ManufacturerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ManufacturerBuilder country(String country) {
            this.country = country;
            return this;
        }

        public Manufacturer build() {
            return new Manufacturer(id, name, country);
        }
    }

}

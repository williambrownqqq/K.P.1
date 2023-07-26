package org.example.domainBuilder;

import org.example.domain.Manufacturer;

public class ConcreteManufaturerBuilder implements ManufacturerBuilder{
    private Integer id;
    private String name;
    private String country;
    @Override
    public ManufacturerBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public ManufacturerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ManufacturerBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public Manufacturer build() {
        return new Manufacturer(id, name, country);
    }
}

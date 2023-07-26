package org.example.domainBuilder;

import org.example.domain.Souvenir;

public class ConcreteSouvenirBuilder implements SouvenirBuilder{
    private Integer id;
    private String name;
    private String manufacturer;
    private String productionDate;
    private double price;
    @Override
    public SouvenirBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public SouvenirBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SouvenirBuilder setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    @Override
    public SouvenirBuilder setProductionDate(String productionDate) {
        this.productionDate = productionDate;
        return this;
    }

    @Override
    public SouvenirBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public Souvenir build() {
        return new Souvenir(id, name, manufacturer, productionDate, price);
    }
}

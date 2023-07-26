package org.example.domainBuilder;

import org.example.domain.Souvenir;

public interface SouvenirBuilder {
    SouvenirBuilder setId(Integer id);
    SouvenirBuilder setName(String name);
    SouvenirBuilder setManufacturer(String manufacturer);
    SouvenirBuilder setProductionDate(String productionDate);
    SouvenirBuilder setPrice(double price);
    Souvenir build();
}

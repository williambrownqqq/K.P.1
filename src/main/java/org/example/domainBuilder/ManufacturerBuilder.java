package org.example.domainBuilder;

import org.example.domain.Manufacturer;

public interface ManufacturerBuilder {
    ManufacturerBuilder setId(Integer id);
    ManufacturerBuilder setName(String name);
    ManufacturerBuilder setCountry(String country);
    Manufacturer build();

}

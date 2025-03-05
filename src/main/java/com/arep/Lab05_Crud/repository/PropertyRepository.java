package com.arep.Lab05_Crud.repository;

import com.arep.Lab05_Crud.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByAddressContaining(String address);
    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Property> findBySizeGreaterThanEqual(Double minSize);
}

package com.arep.Lab05_Crud.service;

import com.arep.Lab05_Crud.model.Property;
import com.arep.Lab05_Crud.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Obtener todas las propiedades
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Obtener una propiedad por ID
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    // Crear una nueva propiedad
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    // Actualizar una propiedad existente
    public Property updateProperty(Long id, Property updatedProperty) {
        return propertyRepository.findById(id)
                .map(property -> {
                    property.setAddress(updatedProperty.getAddress());
                    property.setPrice(updatedProperty.getPrice());
                    property.setSize(updatedProperty.getSize());
                    property.setDescription(updatedProperty.getDescription());
                    return propertyRepository.save(property);
                }).orElseThrow(() -> new RuntimeException("Property not found with id " + id));
    }

    // Eliminar una propiedad
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new RuntimeException("Property not found with id " + id);
        }
        propertyRepository.deleteById(id);
    }
}

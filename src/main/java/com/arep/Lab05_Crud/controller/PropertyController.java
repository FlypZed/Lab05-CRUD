package com.arep.Lab05_Crud.controller;

import com.arep.Lab05_Crud.model.Property;
import com.arep.Lab05_Crud.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property savedProperty = propertyService.createProperty(property);
        return ResponseEntity.ok(savedProperty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        try {
            Property updatedProperty = propertyService.updateProperty(id, property);
            return ResponseEntity.ok(updatedProperty);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

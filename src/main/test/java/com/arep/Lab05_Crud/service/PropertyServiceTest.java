package com.arep.Lab05_Crud.service;

import com.arep.Lab05_Crud.model.Property;
import com.arep.Lab05_Crud.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property;

    @BeforeEach
    void setUp() {
        property = new Property("123 Main St", 250000.0, 120.0, "Beautiful house");
        property.setId(1L);
    }

    @Test
    void getAllPropertiesShouldReturnList() {
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(property));
        List<Property> properties = propertyService.getAllProperties();
        assertEquals(1, properties.size());
        assertEquals("123 Main St", properties.get(0).getAddress());
    }

    @Test
    void getPropertyByIdShouldReturnProperty() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        Optional<Property> foundProperty = propertyService.getPropertyById(1L);
        assertTrue(foundProperty.isPresent());
        assertEquals("123 Main St", foundProperty.get().getAddress());
    }

    @Test
    void getPropertyByIdShouldReturnEmptyOptionalWhenNotFound() {
        when(propertyRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Property> foundProperty = propertyService.getPropertyById(2L);
        assertFalse(foundProperty.isPresent());
    }

    @Test
    void createPropertyShouldReturnSavedProperty() {
        when(propertyRepository.save(any(Property.class))).thenReturn(property);
        Property savedProperty = propertyService.createProperty(property);
        assertNotNull(savedProperty);
        assertEquals("123 Main St", savedProperty.getAddress());
    }

    @Test
    void updatePropertyShouldReturnUpdatedPropertyWhenExists() {
        Property updatedProperty = new Property("456 Elm St", 300000.0, 150.0, "Updated description");
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(updatedProperty);

        Property result = propertyService.updateProperty(1L, updatedProperty);
        assertEquals("456 Elm St", result.getAddress());
    }

    @Test
    void updatePropertyShouldThrowExceptionWhenNotFound() {
        when(propertyRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> propertyService.updateProperty(2L, property));
    }

    @Test
    void deletePropertyShouldCallRepositoryDeleteWhenExists() {
        when(propertyRepository.existsById(1L)).thenReturn(true);
        doNothing().when(propertyRepository).deleteById(1L);
        assertDoesNotThrow(() -> propertyService.deleteProperty(1L));
        verify(propertyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePropertyShouldThrowExceptionWhenNotFound() {
        when(propertyRepository.existsById(2L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> propertyService.deleteProperty(2L));
    }
}

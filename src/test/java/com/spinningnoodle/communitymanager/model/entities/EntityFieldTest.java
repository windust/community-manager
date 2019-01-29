package com.spinningnoodle.communitymanager.model.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityFieldTest {

    @Test
    void getValue() {
        String testValue = "Test Value";
        EntityField<String> field = new EntityField<>(testValue);
        assertEquals(testValue, field.getValue());
    }

    @Test
    void constructorShouldAcceptNull() {
        assertAll(
                () -> assertDoesNotThrow(() -> {
                    EntityField<String> test = new EntityField<>(null);
                }),
                () -> {
                    EntityField<String> test = new EntityField<>(null);
                    assertNull(test.getValue());
                }
        );
    }
}
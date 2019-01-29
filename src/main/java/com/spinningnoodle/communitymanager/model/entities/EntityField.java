package com.spinningnoodle.communitymanager.model.entities;

/**
 * Entity Field
 *
 * This class stores a field for the IEntity classes.
 * This classes purpose is to normalize the datatype of the various fields.
 *
 * @param <T> The datatype of the field which is stored in this object.
 */
public class EntityField<T> {
    private T value;

    public EntityField(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

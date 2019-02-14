package com.spinningnoodle.communitymanager.model.entities;

import com.spinningnoodle.communitymanager.exceptions.AttributeException;
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.observer.Observable;
import java.util.Map;

/**
 * Represents a entity of the data-modeltest
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class IEntity extends Observable {
	private static int nextId = 0;

	private int entityId;
	private int primaryKey;

	public IEntity() {
		setEntityId();
	}

	/**
	 * Factory method to make a Entity based on a map of fields mapped to the Entity's attributes
	 *
	 * @param fields A Key-Value map of Entity attributes where the key is an identifier for the
	 * attribute and the value is the value the attribute takes on
	 * @return A new entity with the values associated identified by the map
	 * @throws AttributeException An exception raised by invalid values for the specified key
	 */
	public abstract IEntity build(Map<String, String> fields) throws AttributeException;

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId() {
		this.entityId = nextId;
		++nextId;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		if(primaryKey < -1 || primaryKey == 0) {
			throw new UnexpectedPrimaryKeyException();
		}
		this.primaryKey = primaryKey;
	}
}

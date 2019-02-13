package com.spinningnoodle.communitymanager.model.entities;

import com.spinningnoodle.communitymanager.exceptions.AttributeException;
import java.util.Map;

/**
 * Represents a entity of the data-model
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public interface IEntity {

	/**
	 * Factory method to make a Entity based on a map of fields mapped to the Entity's attributes
	 *
	 * @param fields A Key-Value map of Entity attributes where the key is an identifier for the
	 * attribute and the value is the value the attribute takes on
	 * @return A new entity with the values associated identified by the map
	 * @throws AttributeException An exception raised by invalid values for the specified key
	 */
	IEntity build(Map<String, String> fields) throws AttributeException;

}

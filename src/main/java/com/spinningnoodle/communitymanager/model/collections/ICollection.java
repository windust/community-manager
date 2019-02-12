package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.IEntity;
import java.util.List;

public interface ICollection<T extends IEntity> {

	/**
	 * Gets all <T> from a DataStorage.
	 *
	 * @param dataStorage The DataStorage used to store venues.
	 */
	void fetchFromDataStorage(DataStorage dataStorage);

	/**
	 * Add one venue to the collection.
	 *
	 * @param entity The <T> to be saved into the Collection .
	 */
	void addToCollection(T entity);

	/**
	 * Gets a <T> from this collection based on it's Entity ID
	 *
	 * @param venueId The Entity ID of the <T> to be retrieved.
	 * @return A <T> who's Entity Id matches the Entity Id passed in
	 * @throws EntityNotFoundException When <T> with the Id passed cannot be found
	 */
	T getById(int venueId) throws EntityNotFoundException;

	/**
	 * Gets all <T>.
	 *
	 * @return A List of all <T>.
	 */
	List<T> getAll();

	/**
	 * Get the amount of Venues stored in the collection
	 *
	 * @return The amount of Venues stored in the collection
	 */
	int size();

	/**
	 * Removes all <T> from the collection
	 */
	void clear();
}

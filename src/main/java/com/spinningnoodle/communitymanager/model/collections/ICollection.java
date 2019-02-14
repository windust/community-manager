package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.IEntity;
import com.spinningnoodle.communitymanager.model.observer.IObserver;
import com.spinningnoodle.communitymanager.model.observer.Observable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ICollection<T extends IEntity> implements IObserver<T> {
	DataStorage dataStorage;
	Map<Integer, T> entities = new HashMap<>();

	public ICollection(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	/**
	 * Gets all <T> from a DataStorage.
	 *
	 * @param dataStorage The DataStorage used to store venues.
	 */
	public abstract void fetchFromDataStorage();

	/**
	 * Add one venue to the collection.
	 *
	 * @param entity The <T> to be saved into the Collection .
	 */
	public void addToCollection(T entity) {
		entities.put(entity.getEntityId(), entity);
	}

	/**
	 * Gets a <T> from this collection based on it's Entity ID
	 *
	 * @param entityId The Entity ID of the <T> to be retrieved.
	 * @return A <T> who's Entity Id matches the Entity Id passed in
	 * @throws EntityNotFoundException When <T> with the Id passed cannot be found
	 */
	public T getById(int entityId) throws EntityNotFoundException {
		if(!entities.containsKey(entityId)) {
			throw new EntityNotFoundException();
		}
		return entities.get(entityId);
	}

	/**
	 * Gets all <T>.
	 *
	 * @return A List of all <T>.
	 */
	public List<T> getAll() {
		return new ArrayList<>(entities.values());
	}

	/**
	 * Get the amount of Venues stored in the collection
	 *
	 * @return The amount of Venues stored in the collection
	 */
	public int size() {
		return entities.size();
	}

	/**
	 * Removes all <T> from the collection
	 */
	public void clear() {
		entities.clear();
	}

	public abstract String getTableName();
}

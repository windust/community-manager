package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.IEntity;
import com.spinningnoodle.communitymanager.model.observer.IObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> Entity type
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class ICollection<T extends IEntity> implements IObserver<T> {
	private DataStorage dataStorage;
	private Map<Integer, T> entities = new HashMap<>();

	protected DataStorage getDataStorage() {
		return dataStorage;
	}

	protected boolean dataStorageUpdate(String tableName, String primaryKey, String attribute, String newValue) {
		return dataStorage.update(tableName, primaryKey, attribute, newValue);
	}

	protected Collection<T> getEntitiesValues() {
		return this.entities.values();
	}

	/**
	 * @param entity The entity to store in the collection
	 */
	protected void addToEntities(T entity) {
		entities.put(entity.getEntityId(), entity);
	}

	/**
	 * @param dataStorage the data storage to use as a database
	 */
	public ICollection(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
		fetchFromDataStorage();
	}

	/**
	 * Gets all <T> from a DataStorage.
	 *
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
		fetchFromDataStorage();
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

	/**
	 * @return the table this collection uses
	 */
	public abstract String getTableName();

	@Override
	public String toString() {
		return "ICollection{" +
			"dataStorage=" + dataStorage +
			", entities=" + entities +
			'}';
	}
}
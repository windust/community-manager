package com.spinningnoodle.communitymanager.model.collections;
/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */
import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Entity;
import com.spinningnoodle.communitymanager.model.observer.Observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T> Entity type
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class EntityCollection<T extends Entity> implements Observer<T> {
	private DataStorage dataStorage;
	private Map<Integer, T> entities = new HashMap<>();
	private final String TABLE_NAME;

	/**
	 * @param dataStorage the data storage to use as a database
	 */
	public EntityCollection(DataStorage dataStorage, String tableName) {
		this.dataStorage = dataStorage;
		this.TABLE_NAME = tableName;
		fetchFromDataStorage();
	}

	protected DataStorage getDataStorage() {
		return dataStorage;
	}
	
	protected String getTableName(){
		return TABLE_NAME;
	}

	protected boolean dataStorageUpdate(String tableName, String primaryKey, String attribute, String newValue) {
		fetchFromDataStorage();
		return dataStorage.update(tableName, primaryKey, attribute, newValue);
	}

	protected java.util.Collection<T> getEntitiesValues() {
		fetchFromDataStorage();
		return this.entities.values();
	}

	/**
	 * @param entity The entity to store in the collection
	 */
	protected void addToEntities(T entity) {
	  entity.atachObserver(this);
	  entities.put(entity.getEntityId(), entity);
	}

	/**
	 * Gets all <T> from a DataStorage.
	 *
	 */
	public abstract void fetchFromDataStorage();

	/**
	 * Add one venue to the collection.
	 *
	 * @param entity The <T> to be saved into the EntityCollection .
	 */
	public void addToCollection(T entity) {
		fetchFromDataStorage();
		addToEntities(entity);
	}

	/**
	 * Gets a <T> from this collection based on it's Entity ID
	 *
	 * @param entityId The Entity ID of the <T> to be retrieved.
	 * @return A <T> who's Entity Id matches the Entity Id passed in
	 * @throws EntityNotFoundException When <T> with the Id passed cannot be found
	 */
	public T getById(int entityId) throws EntityNotFoundException {
		fetchFromDataStorage();

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
		fetchFromDataStorage();
		return entities.size();
	}

	/**
	 * Removes all <T> from the collection
	 */
	public void clear() {
		entities.clear();
	}

	@Override
	public String toString() {
		return "EntityCollection{" +
			"dataStorage=" + dataStorage +
			", entities=" + entities +
			'}';
	}
}
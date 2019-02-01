package com.spinningnoodle.communitymanager.datastorage;

import java.util.List;
import java.util.Map;

/**
 * Data Storage is the interface that all data storage implementations meet.
 *
 * All implementations should include the following constructors:
 *      DataStorage(String storageID) - opens existing DataStorage;
 * @author  Cream 4 UR Coffee
 * @version 0.1
 * @since   2019-02-01
 */
public interface DataStorage {

    /**
     * create entry creates a new row in the database.
     *
     * @return boolean: true if entry was added
     */
    boolean createEntry();

    /**
     * readAll returns a list of all the data in the requested table.
     * each row is represented as a hashmap in the list.
     *
     * @param tableName string: name of table
     * @return List<Map<String,String>> of all data in specified table
     * @throws IllegalArgumentException if table does not exist
     */
    List<Map<String, String>> readAll(String tableName);

    /**
     * readAll returns a list of all the data in the requested table.
     * each row is represented as a hashmap in the list.
     *
     * @param tableName string: name of table
     * @param primaryKey string: unique key for row
     * @param attribute string: attribute to be changed
     * @param newValue string: the new value
     * @return boolean, if it updated
     * @throws IllegalArgumentException if table or primary key does not exist
     */
    boolean update(String tableName, String primaryKey, String attribute, String newValue);

    /**
     * readAll returns a list of all the data in the requested table.
     * each row is represented as a hashmap in the list.
     *
     * @param tableName string: name of table
     * @param primaryKey
     * @return boolean if deleted
     * @throws IllegalArgumentException if table does not exist
     */
    boolean deleteEntry(String tableName, String primaryKey);

    String getName();
    void setName(String name);
    String getStorageID();
    void setStorageID(String storageID);

    String[] getTableNames();

}

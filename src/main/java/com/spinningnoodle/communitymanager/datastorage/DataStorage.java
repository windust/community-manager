package com.spinningnoodle.communitymanager.datastorage;

import java.io.IOException;
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
     * @throws IOException if error getting data storage based on storageID
     */
    List<Map<String, String>> readAll(String tableName) throws IOException;

    /**
     * update changes one entry based on the specified table name, primary key, and
     * attribute. If the attribute does not exist
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
     * deleteEntry deletes the row specified by the primaryKey in the table specified
     *
     * @param tableName string: name of table
     * @param primaryKey string: unique identifier of row.
     * @return boolean if deleted
     * @throws IllegalArgumentException if table does not exist
     */
    boolean deleteEntry(String tableName, String primaryKey);

    /**
     * getName returns the name of the data storage
     *
     * @return String of data storage name
     */
    String getName();

    /**
     * setName sets the name of the data storage
     *
     * @param name string: name of data storage
     * @return
     */
    void setName(String name);

    /**
     * Gets the storage id.
     *
     * @return String with the storage id
     */
    String getStorageID();

    /**
     * sets the name of the storage ID
     *
     * @param storageID string: the identifier of the data storage
     */
    void setStorageID(String storageID);

    /**
     * getTableNames gets the names of all the tables in the data storage
     * @return String array with the table names
     */
    String[] getTableNames();

}

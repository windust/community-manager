package com.spinningnoodle.communitymanager.datastorage;

import java.util.List;
import java.util.Map;

/**
 * Data Storage
 *
 * All implementations should include the following constructors:
 *      DataStorage(String storageID) - opens existing DataStorage;
 *
 */
public interface DataStorage {

    boolean createEntry();
    List<Map<String, String>> readAll(String tableName);
    boolean update(String tableName, String primaryKey, String attribute, String newValue);
    boolean deleteEntry(String tableName, String primaryKey);

    String getName();
    void setName(String name);
    String getStorageID();
    void setStorageID(String storageID);

    String[] getTableNames();

}

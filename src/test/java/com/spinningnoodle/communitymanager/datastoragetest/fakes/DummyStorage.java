package com.spinningnoodle.communitymanager.datastoragetest.fakes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dummy Storage implements Data Storage, the interface that all data storage implementations meet.
 * It can be used for testing model functionality in the absence of another implementation.
 *
 * All implementations should include the following constructors:
 *      DataStorage(String storageID) - opens existing DataStorage;
 * @author  Cream 4 UR Coffee
 * @version 0.1
 * @since   2019-02-01
 */
public class DummyStorage implements DataStorage {

    private String name;
    private String storageID;
    private Map<String,String> tableNames;
    private List<Map<String, String>> data;

    /**
     * DummyStorage(String storageID) - opens existing DummyStorage;
     *
     * @param storageID String of the Data Storage id
     * @throws GeneralSecurityException if cannot connect to data storage
     * @throws IOException if Credentials are not found.
     */
    public DummyStorage(String storageID) throws GeneralSecurityException {
        if(!storageID.equals("123")) {
            throw new GeneralSecurityException("Could not connect to data storage,");
        }
        this.storageID = storageID;
        this.name = storageID;
        this.tableNames = new HashMap<>();
        tableNames.put("speakers", "1");
        tableNames.put("venues", "2");
        tableNames.put("meetups", "3");
        List<Map<String, String>> list = new ArrayList<>();
        Map<String,String> row = new HashMap<>();
        row.put("primaryKey", "1");
        row.put("name","Excellent");
        row.put("address","100 Nowhere St");
        row.put("capacity", "100");
        row.put("contactPerson", "Freddy");
        row.put("contactEmail", "freddy@excellent.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","123N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("name","Amazing");
        row.put("address","200 Nowhere St");
        row.put("capacity", "150");
        row.put("contactPerson", "Nimret");
        row.put("contactEmail", "nimret@amazing.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","143N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        data = list;

    }

    @Override
    public boolean createEntry() {
        return false;
    }

    @Override
    public List<Map<String, String>> readAll(String tableName) {
        if(Arrays.stream(tableNames.keySet().toArray()).noneMatch(tableName.toLowerCase()::equals)){
            throw new IllegalArgumentException("Table Name: " + tableName + " does not exist.");
        }
        return data;
    }

    @Override
    public boolean update(String tableName, String primaryKey, String attribute, String newValue) {
        for(Map<String,String> row : data){
            if(row.get("primaryKey").equals(primaryKey)){
                row.put(attribute,newValue);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteEntry(String tableName, String primaryKey) {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStorageID() {
        return storageID;
    }

    @Override
    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }

    @Override
    public Map<String, String> getTableNames() {
        return tableNames;
    }

    @Override
    public String toString() {
        return "DummyStorage{" +
            "name='" + name + '\'' +
            ", storageID='" + storageID + '\'' +
            ", tableNames=" + Arrays.toString(tableNames.keySet().toArray()) +
            ", data=" + data +
            '}';
    }
}

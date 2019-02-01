package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;


import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DummyStorage implements DataStorage {

    private String name;
    private String storageID;
    private String[] tableNames;
    List<Map<String, String>> data;

    public DummyStorage(String storageID) throws ConnectException {
        if(storageID != "123") throw new ConnectException("Could not connect to data storage,");
        this.storageID = storageID;
        this.name = storageID;
        this.tableNames = new String[]{"speakers","venues"};
        List<Map<String, String>> list = new ArrayList<>();
        Map<String,String> row = new HashMap<>();
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
        if(!Arrays.stream(tableNames).anyMatch(tableName.toLowerCase()::equals)){
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
    public String[] getTableNames() {
        return tableNames;
    }
}

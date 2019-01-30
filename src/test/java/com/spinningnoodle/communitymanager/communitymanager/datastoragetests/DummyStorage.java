package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import java.net.ConnectException;

public class DummyStorage implements DataStorage {

    private String name;
    private String storageID;
    private String[] tableNames;

    public DummyStorage(String storageID) throws ConnectException {
        this.storageID = storageID;
        this.name = storageID;
        if(storageID != "123") throw new ConnectException("Could not connect to data storage,");
    }

    @Override
    public boolean createEntry() {
        return false;
    }

    @Override
    public String readAll(String tableName) {
        return "{row1:{name:'joe'}}";
    }

    @Override
    public boolean update(String tableName, String primaryKey, String attribute, String newValue) {
        return false;
    }

    @Override
    public boolean deleteEntry(String tableName, String primaryKey) {
        return false;
    }

    @Override
    public String getName() {
//        return null;
        return name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getStorageID() {
        return storageID;
//        return null;
    }

    @Override
    public void setStorageID(String storageID) {

    }

    @Override
    public String[] getTableNames() {
        return null;
    }
}

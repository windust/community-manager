package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;

public class DummyStorage implements DataStorage {

    private String name;
    private String storageID;
    private String[] tableNames;

    public DummyStorage(String storageID) {
        this.storageID = storageID;
        this.name = storageID;
    }

    @Override
    public boolean createEntry() {
        return false;
    }

    @Override
    public String readAll(String tableName) {
        return null;
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
    public String getNeme() {
        return name;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getStorageID() {
        return storageID;
    }

    @Override
    public void setStorageID(String storageID) {

    }

    @Override
    public String[] getTableNames() {
        return null;
    }
}

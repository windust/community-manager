package com.spinningnoodle.communitymanager.datastorage;

public class GoogleSheets implements DataStorage {

    private String name;
    private String storageID;
    private String[] tableNames;

    public GoogleSheets(String storageID) {
        this.storageID = storageID;
        //open google sheets
        //get name
        //get table names (Might be doing too much!)
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
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getStorageID() {
        return null;
    }

    @Override
    public void setStorageID(String storageID) {
    }

    @Override
    public String[] getTableNames() {
        return null;
    }
}

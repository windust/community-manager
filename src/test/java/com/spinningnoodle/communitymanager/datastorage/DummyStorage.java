package com.spinningnoodle.communitymanager.datastorage;
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
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dummy Storage implements Data Storage, the interface that all venueData storage implementations meet.
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
    private List<Map<String, String>> venueData;
    private List<Map<String, String>> meetupData;
    
    /**
     * DummyStorage(String storageID) - opens existing DummyStorage;
     *
     * @param storageID String of the Data Storage id
     * @throws GeneralSecurityException if cannot connect to venueData storage
     * @throws IOException if Credentials are not found.
     */
    public DummyStorage(String storageID) throws GeneralSecurityException {
        if(!storageID.equals("123")) {
            throw new GeneralSecurityException("Could not connect to venueData storage,");
        }
        this.storageID = storageID;
        this.name = storageID;
        this.tableNames = new HashMap<>();
        tableNames.put("speakers", "1");
        tableNames.put("venues", "2");
        tableNames.put("meetups", "3");
        venueData = new ArrayList<>();
        meetupData = new ArrayList<>();
        
        createVenues();
        createMeetups();
    }
    
    private void createVenues(){
        Map<String, String> row = new HashMap<>();
        
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
        venueData.add(row);
        
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
        venueData.add(row);
    }
    
    private void createMeetups(){
        Map<String, String> row = new HashMap<>();
        
        row = new HashMap<>();
        row.put("primaryKey", "1");
        row.put("date","01/14/2019");
        row.put("speaker","Purple");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", "Excellent");
        meetupData.add(row);
        
        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("date","02/19/2019");
        row.put("speaker","Yellow");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", "Amazing");
        meetupData.add(row);
        
        row = new HashMap<>();
        row.put("primaryKey", "3");
        row.put("date","03/22/2019");
        row.put("speaker","John Doe");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", null);
        meetupData.add(row);
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
        if(tableName.equals("venues")){
            return venueData;
        }
        else{
            return meetupData;
        }
    }
    
    @Override
    public boolean update(String tableName, String primaryKey, String attribute, String newValue) {
        if(tableName.equals("venues")) {
            for (Map<String, String> row : venueData) {
                if (row.get("primaryKey").equals(primaryKey)) {
                    row.put(attribute, newValue);
                    return true;
                }
            }
            return false;
        }
        else{
            for (Map<String, String> row : meetupData) {
                if (row.get("primaryKey").equals(primaryKey)) {
                    if(row.get(attribute) == null) {
                        row.put(attribute, newValue);
                        return true;
                    }
                }
            }
            return false;
        }
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
            ", venueData=" + venueData +
            '}';
    }
}

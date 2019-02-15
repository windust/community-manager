package com.spinningnoodle.communitymanager.datastorage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * GoogleSheets implements Data Storage, the interface that all data storage implementations meet.
 * It uses Google Sheets to store data.
 *
 * All implementations should include the following constructors:
 *      DataStorage(String storageID) - opens existing DataStorage;
 *
 * @author  Cream 4 UR Coffee
 * @version 0.1
 * @since   2019-02-04
 */
public class GoogleSheets implements DataStorage {

    private Sheets service;
    private String name;
    private String storageID;
    private String[] tableNames;

    private static final String APPLICATION_NAME = "Community Manager";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> READ_WRITE_SCOPE = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    /**
     * DataStorage(String storageID) - opens existing DataStorage;
     *
     * @param storageID String of the Data Storage id
     * @throws GeneralSecurityException
     * @throws IOException if Credentials are not found.
     */
    public GoogleSheets(String storageID) throws GeneralSecurityException, IOException {
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
    public List<Map<String, String>> readAll(String tableName) {
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
    public String getName() {
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

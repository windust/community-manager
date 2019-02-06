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
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
        //get name
        //get table names (Might be doing too much!)
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        //Not sure if I should run this off of GoogleSheets or the main community manager
        //TBD working but still claims to be quickstart! Haven't found where or why!
        InputStream in = GoogleSheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        InputStream in = GoogleSheets.class.getResourceAsStream(/);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets
            .load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, READ_WRITE_SCOPE)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Override
    public boolean createEntry() {
        return false;
    }

    @Override
    public List<Map<String, String>> readAll(String tableName) throws IOException {
        String range = tableName;
        List<Map<String, String>> data = new ArrayList<>();
        if(!service.spreadsheets().get(storageID).isEmpty()) {
            ValueRange response = service.spreadsheets().values()
                .get(storageID, range)
                .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                return null;
            } else {
                List<Object> attributesNames = values.get(0);
                List<String> attributes = new ArrayList<>();
                for(Object attributeName: attributesNames){
                    if(attributeName.getClass().toString().equals("class java.lang.String")){
                        attributes.add(attributeName.toString());
                    } else {
                        System.out.println(attributeName.getClass().toString());
                    }
                }

                for(int rowNum = 1; rowNum < values.size(); rowNum++){
                    HashMap<String,String> row = new HashMap<>();
                    List<Object> rawRow = values.get(rowNum);
                    for(int columnNum = 0; columnNum < attributes.size(); columnNum++){
                        Object value = (columnNum < rawRow.size()) ? rawRow.get(columnNum) : "";
                        if(value.getClass().toString().equals("class java.lang.String")){
                            row.put(attributes.get(columnNum),value.toString());
                        } else {
                            //TBD what is the best way to handle this
                            System.out.println(value.getClass().toString());
                            row.put(attributes.get(columnNum),"Failed to Return Properly");
                        }
                    }
                    data.add(row);

                }
            }
        } else {
            System.out.println("The requested spreadsheet was empty.");
        }
        return data;
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
        return storageID;
    }

    @Override
    public void setStorageID(String storageID) {
    }

    @Override
    public String[] getTableNames() {
        //TBD get from actual data storage
        String[] fakeNames = {"venues","speakers","meetups"};
        return fakeNames;
    }
}

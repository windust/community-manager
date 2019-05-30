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
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    Sheets service;
    private String storageID;
    Spreadsheet spreadsheet;

    private static final String APPLICATION_NAME = "Community Manager";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> READ_WRITE_SCOPE = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * DataStorage(String storageID) - opens existing DataStorage;
     *
     * @throws GeneralSecurityException
     * @throws IOException if Credentials are not found.
     */
    public GoogleSheets(String idFileName, String spreadsheetName) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
        try {
            spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                    .setTitle(spreadsheetName));
            spreadsheet = service.spreadsheets().create(spreadsheet)
                .setFields("spreadsheetId")
                .execute();
            storageID = spreadsheet.getSpreadsheetId();
            System.out.println(storageID);
            FileWriter fileWriter = new FileWriter(idFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(storageID);
            printWriter.close();
        } catch (IOException e) {
            throw new IOException("Unable to connect to spreadsheet.");
        }

    }

    /**
     * DataStorage(String storageID) - opens existing DataStorage;
     *
     * @param storageID String of the Data Storage id
     * @throws GeneralSecurityException
     * @throws IOException if Credentials are not found.
     */
    public GoogleSheets(String storageID) throws GeneralSecurityException, IOException {
        this.storageID = storageID;

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
        try {
            spreadsheet = service.spreadsheets().get(storageID).execute();
        } catch (IOException e) {
            throw new IOException("Unable to connect to spreadsheet.");
        }
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        //TBD working but still claims to be quickstart! Haven't found where or why!
        InputStream in = GoogleSheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets
            .load(JSON_FACTORY, new InputStreamReader(in));
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
        List<List<Object>> values = getData(tableName);

        if (values == null || values.isEmpty()) {
            return null;
        } else {
            return convertTableDataToHashMap(values);
        }
    }

    private List<Map<String, String>> convertTableDataToHashMap(List<List<Object>> values) {
        List<Map<String, String>> data = new ArrayList<>();
        List<String> attributes = getAttributesAsStrings(values);

        for (int listNum = 1; listNum < values.size(); listNum++) {
            HashMap<String, String> row = new HashMap<>();
            List<Object> rawRow = values.get(listNum);
            int rowNum = listNum + 1;
            row.put("primaryKey",String.valueOf(rowNum));
            for (int columnNum = 0; columnNum < attributes.size(); columnNum++) {
                Object value = (columnNum < rawRow.size()) ? rawRow.get(columnNum) : "";
                if (value.getClass().toString().equals("class java.lang.String")) {
                    row.put(attributes.get(columnNum).trim(), value.toString().trim());
                } else {
                    //TBD what is the best way to handle this
                    System.out.println(value.getClass().toString());
                    row.put(attributes.get(columnNum).trim(), "Failed to Return Properly");
                }
            }
            data.add(row);

        }
        return data;
    }

    private List<String> getAttributesAsStrings(List<List<Object>> values) {
        List<Object> attributesNames = values.get(0);
        List<String> attributes = new ArrayList<>();
        for(Object attributeName: attributesNames){
            if (attributeName instanceof String) {
                attributes.add(attributeName.toString());
            } else {
                System.out.println("Could not convert attribute name to String: " + attributeName.getClass().toString());
            }
        }
        return attributes;
    }


    @Override
    public boolean update(String tableName, String primaryKey, String attribute, String newValue) {
        List<List<Object>> values;
        try {
            values = getData(tableName);
        } catch (IOException e) {
            return false;
        }

        if (values == null || values.isEmpty()) {
            return false;
        }

        int rowNumberToUpdate = getRowNumber(values, primaryKey);
        String columnLetterToUpdate = getColumnLetter(values.get(0), attribute);

        String cell = tableName + "!" + columnLetterToUpdate + rowNumberToUpdate;

        return update(cell, newValue);
    }

    private boolean update(String cell, String newValue){

        if(cell.contains("null") || isRow1(cell)) return false;

        List<List<Object>> values = Collections.singletonList(
            Collections.singletonList(
                newValue
            )
        );

        ValueRange body = new ValueRange().setValues(values);
        try {
            UpdateValuesResponse result =
                service.spreadsheets().values().update(storageID, cell, body)
                    .setValueInputOption("RAW")
                    .execute();
            return result.getUpdatedCells() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean isRow1(String cell){
        String pattern = "(?i)(.*)[a-z]1";

        return cell.matches(pattern);
    }

    @Override
    public boolean deleteEntry(String tableName, String primaryKey) {
        return false;
    }

    @Override
    public String getName() {
        return spreadsheet.getProperties().getTitle();
    }

    @Override
    public void setName(String name) {
        spreadsheet.getProperties().setTitle(name);
    }

    @Override
    public String getStorageID() {
        return storageID;
    }

    @Override
    public void setStorageID(String storageID) {
    }

    @Override
    public Map<String, String> getTableNames() {
        List<Sheet> sheets = spreadsheet.getSheets();

        Map<String,String> sheetNames = new HashMap<>();
        for(Sheet sheet: sheets){
            sheetNames.put(sheet.getProperties().getTitle(),
                sheet.getProperties().getSheetId().toString());
        }

        return sheetNames;
    }


    List<List<Object>> getData(String tableName) throws IOException{
        String range = tableName;
        try {
            if (!spreadsheet.isEmpty()) {
                ValueRange response = service.spreadsheets().values()
                    .get(storageID, range)
                    .execute();
                return response.getValues();
            }
        } catch (Exception e){
            throw new IOException("Unable to access table "+tableName);
        }
        return null;
    }

    private int getRowNumber(List<List<Object>> values, String primaryKey){

        int rowNumber = 1;

        if(Integer.parseInt(primaryKey) <= values.size()){
            rowNumber = Integer.parseInt(primaryKey);
        }
        return rowNumber;
    }

    private String getColumnLetter(List<Object> attributes, String attribute){

        int a = 65;
        String columnLetter = null;
        for(int i = 0; i < attributes.size(); i++){
            if(attribute.equals(attributes.get(i).toString())){
                return ""+ (char)(a + i);
            }
        }

        return columnLetter;
    }
}
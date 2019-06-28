package com.spinningnoodle.communitymanager;


import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.DataManager;
import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Here in the configuration class we set up a few beans to be
 * run first when the application starts. The beans we have starting
 * is the createManager method that returns a new GoogleSheetsManager
 * and a createStorage that passes a String storageID to the Google Sheets.
 * @author Cream 4 Ur Coffee
 * @version 0.1
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    /**
     * Here a new GoogleSheetsManager is created when the app is started up.
     * @return instance of GoogleSheetsManager class
     */
    @Bean
    @Profile({"dev","prod"})
    public DataManager createManager() {
        return new GoogleSheetsManager();
    }

    /**
     * Creates a new GoogleSheets with storageID when the app is started.
     * @param storageID string to gain access to the google sheet.
     * @return instance of GoogleSheets with the storageID passed in. 
     * @throws GeneralSecurityException
     * @throws IOException
     */
    @Bean
    @Profile({"dev"})
//    public DataStorage createDevStorage(@Value("${storageID}") String storageID) throws GeneralSecurityException, IOException{
//        return new GoogleSheets(storageID);
//    }
    public DataStorage createDevStorage(@Value("1kmPdjgZ55alsUaSp5basbdtZZAJmG3E2HCc5h7h74WU") String storageID) throws GeneralSecurityException, IOException{
        return new GoogleSheets("http://ec2-52-14-112-68.us-east-2.compute.amazonaws.com","/credentials.json",storageID);
    }

    /**
     * Creates a new GoogleSheets with storageID when the app is started.
     * @param storageID string to gain access to the google sheet.
     * @return instance of GoogleSheets with the storageID passed in.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    @Bean
    @Profile({"prod"})
    public DataStorage createStorage(@Value("${storageID}") String storageID) throws GeneralSecurityException, IOException{
        return new GoogleSheets(storageID);
    }
}

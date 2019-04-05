package com.spinningnoodle.communitymanager;


import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.DataManager;
import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    
    @Bean
    public DataManager createManager() {
        return new GoogleSheetsManager();
    }
    
    @Bean
    public DataStorage createStorage(@Value("113AbcCLo0ZAJLhoqP0BXaJPRlzslESkkk98D44Ut1Do") String storageID) throws GeneralSecurityException, IOException{
        return new GoogleSheets(storageID);
    }
}

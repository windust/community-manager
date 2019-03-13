package com.spinningnoodle.communitymanager;


import com.spinningnoodle.communitymanager.model.DataManager;
import com.spinningnoodle.communitymanager.model.GoogleSheetsManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    
    
    @Bean
    public DataManager createManager(@Value("${storageID}") String storageID) {
        return new GoogleSheetsManager(storageID);
    }
    
}

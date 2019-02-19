package com.spinningnoodle.communitymanager.model.entities;

import java.util.Map;

/**
 * Class used strictly for testing TokenEntity abstract class
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class DummyToken extends TokenEntity {
    @Override
    public Entity build(Map<String, String> fields){
        DummyToken temp = new DummyToken();
        
        temp.setName(fields.get("name"));
        temp.setToken(fields.get("token"));
        
        return temp;
    }
    
}

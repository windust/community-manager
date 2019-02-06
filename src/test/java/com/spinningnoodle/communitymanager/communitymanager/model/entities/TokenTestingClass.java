package com.spinningnoodle.communitymanager.communitymanager.model.entities;

import java.util.Map;

/**
 * Class used strictly for testing TokenEntity abstract class
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class TokenTestingClass extends TokenEntity{
    @Override
    public IEntity build(Map<String, String> fields){
        TokenTestingClass temp = new TokenTestingClass();
        
        temp.setName(fields.get("name"));
        temp.setToken(fields.get("token"));
        
        return temp;
    }
    
}

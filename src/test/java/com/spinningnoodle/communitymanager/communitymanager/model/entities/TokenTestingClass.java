package com.spinningnoodle.communitymanager.communitymanager.model.entities;

import java.util.Map;

public class TokenTestingClass extends TokenEntity{
    @Override
    public IEntity build(Map<String, String> fields){
        TokenTestingClass temp = new TokenTestingClass();
        
        temp.setName(fields.get("name"));
        temp.setToken(fields.get("token"));
        
        return temp;
    }
    
}

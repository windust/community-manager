package com.spinningnoodle.communitymanager.communitymanager.model.entities;

import java.util.UUID;

public abstract class TokenEntity implements IEntity {
    private String name;
    private String token;
    
    public String getToken(){
        if(token == null){
            String entityName = Character.toString(name.charAt(0)).toUpperCase() +
                                name.substring(1).toLowerCase().replaceAll("\\p{javaWhitespace}", "");
            
            token = UUID.randomUUID().toString() + entityName;
        }
        
        return token;
    }
    
    public void setToken(String token){
        this.token = token;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}

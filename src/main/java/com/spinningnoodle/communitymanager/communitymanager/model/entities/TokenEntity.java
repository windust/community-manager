package com.spinningnoodle.communitymanager.communitymanager.model.entities;

import java.util.UUID;

/**
 * Abstract class that implements token and name fields
 * as well as there setters and getters
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class TokenEntity implements IEntity {
    private String name;
    private String token;
    
    /**
     * Returns the token if it currently has one otherwise
     * creates and sets token variable
     * @return token - token for specific entity
     */
    public String getToken(){
        if(token == null){
            generateToken();
        }
        
        return token;
    }
    
    private void generateToken() {
        String entityName = Character.toString(name.charAt(0)).toUpperCase() +
                            name.substring(1).toLowerCase().replaceAll("\\p{javaWhitespace}", "");
        
        token = UUID.randomUUID().toString() + entityName;
    }
    
    /**
     * @param token - token for specific entity
     */
    public void setToken(String token){
        this.token = token;
    }
    
    /**
     * @return name - name of entity
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name - name for entity
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
            "name='" + name + '\'' +
            ", token='" + token + '\'' +
            '}';
    }
}

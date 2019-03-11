package com.spinningnoodle.communitymanager.model.entities;
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
import java.util.UUID;

/**
 * Abstract class that implements token and name fields
 * as well as there setters and getters
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public abstract class TokenEntity extends Entity {
    private String name;
    private String token = "";
    
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
        
        setToken(UUID.randomUUID().toString() + entityName);

    }
    
    /**
     * @param token - token for specific entity
     */
    public void setToken(String token){
        this.token = token;

        this.notifyObservers();
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

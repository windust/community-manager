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
public abstract class ResponderEntity extends Entity {
    private static final int UUID_MIN_LENGTH = 32;

    private String name;
    private Response response;
    private String token = "";

    /**
     * If the current token is not in a valid format a new token will be generated.
     *
     * @return token - token of this entity
     */
    public String getOrGenerateToken() {
        if(!isTokenValid(token)) {
            setToken(generateNewToken());
        }

        return token;
    }

    /**
     * Returns the token as-is
     *
     * @return token - token of this entity
     */
    public String getToken() {
        return token;
    }

    /**
     * Validates a token is in a valid format, set the entities token, and notify observers of a change in this entity.
     *
     * @param token The token of this entity
     */
    protected void setToken(String token) {
        this.token = token;

        this.notifyObservers();
    }

    /**
     * Returns if a token does not match the expected format of a token.
     *
     * @param token the token which is being checked
     * @return true - the token is not valid; false - the token is valid
     */
    protected static boolean isTokenValid(String token) {
        return ((token != null) && token.length() >= UUID_MIN_LENGTH);
    }

    /**
     * Generates a new token with the format name-UUID
     *
     * @return The token generated
     */
    protected String generateNewToken() {

      String entityName = "";
        if(name != null) {
          entityName = Character.toString(name.charAt(0)).toUpperCase() +
                      name.substring(1).toLowerCase().replaceAll("\\p{javaWhitespace}", "");
          entityName += '-';
        }

        return entityName + UUID.randomUUID().toString();
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

    public Response getResponse(){
        return response;
    }
    
    public void setResponse(Response response){
        this.response = response;
    }
    
    Response convertResponse(String response){
        switch (response){
            case "yes":
                return Response.ACCEPTED;
            case "no":
                return Response.DECLINED;
            default:
                return Response.UNDECIDED;
        }
    }
    
    @Override
    public String toString() {
        return "TokenEntity{" +
            "name='" + name + '\'' +
            ", token='" + token + '\'' +
            '}';
    }
    
    public enum Response{
        ACCEPTED,
        DECLINED,
        UNDECIDED
    }
}

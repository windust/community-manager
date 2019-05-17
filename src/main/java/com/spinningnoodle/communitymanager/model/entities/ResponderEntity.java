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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
    private LocalDate requestedDate;
    protected Map<Receipt, String> messages;

    /**
     * Default ResponderEntity constructor.
     */
    public ResponderEntity() {}

    /**
     * ResponderEntity constructor that takes in a int parameter
     * primaryKey and passes it to the super constructor.
     * @param primaryKey
     */
    public ResponderEntity(int primaryKey) {
        super(primaryKey);
    }

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
          entityName = name.replaceAll("\\p{javaWhitespace}", "");
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

    /**
     * Getter for response.
     * @return the response from the venue or food sponsor.
     */
    public Response getResponse(){
        return response;
    }

    /**
     * Setter for response.
     * @param response from the venue or food sponsor.
     */
    public void setResponse(Response response){
        this.response = response;
    }
    
    /**
     * @return The date that was this venue was requested by Java User Group
     */
    public LocalDate getRequestedDate() {
        return requestedDate;
    }
    
    /**
     * @param requestedDate The date this venue was requested by Java User Group
     */
    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
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

    /**
     * Getter for message.
     * @param receipt messages
     * @return messages.
     */
    public String getMessage(Receipt receipt){
        return this.messages.get(receipt);
    }
    
    protected Map<Receipt, String> generateMessages(){
        Map<Receipt, String> messages = new HashMap<>();
        messages.put(Receipt.NO, "Thank you for your consideration.");
        return messages;
    }
    
    @Override
    public String toString() {
        return "TokenEntity{" +
            "name='" + name + '\'' +
            ", token='" + token + '\'' +
            '}';
    }

    public enum Response{
        ACCEPTED("yes"),
        DECLINED("no"),
        UNDECIDED("");

        private String friendlyName;

        Response(String response){
            this.friendlyName = response;
        }

        public String getFriendlyName(){
            return friendlyName;
        }

    }
    
    public enum Receipt{
        NO,
        NOT_RESPONDED,
        ALREADY_TAKEN,
        ACCEPTED,
        ACCEPTED_PLUS;
    }
}

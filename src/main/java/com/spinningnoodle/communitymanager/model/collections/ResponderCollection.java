package com.spinningnoodle.communitymanager.model.collections;
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

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.entities.Entity;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Receipt;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ResponderCollection<T extends ResponderEntity> extends EntityCollection<T> {

    protected Map<String,Integer> nameIdJunction = new HashMap<>();

    /**
     * ResponderCollection constructor that takes in a String
     * parameter tableName with a call to in super constructor.
     * @param tableName
     */
    public ResponderCollection(String tableName){
        super(tableName);
    }

    /**
     * ResponderCollection constructor that takes in two parameters
     * DataStorage dataStorage and String tableName and has a call
     * to a super constructor.
     * @param dataStorage
     * @param tableName
     */
    public ResponderCollection(DataStorage dataStorage, String tableName){
        super(dataStorage, tableName);
    }

    public void addToCollection(T entity) throws IllegalArgumentException {
        nameIdJunction.put(entity.getName(),entity.getPrimaryKey());
        super.addToCollection(entity);
    }

    public ResponderEntity getResponderByName(String name){
        return entities.get(nameIdJunction.get(name));
    }

    public T getEntityByToken(String token) {
        for(T tokenEntity : getEntitiesValues()) {
            if(tokenEntity.getToken().equals(token)) {
                return tokenEntity;
            }
        }
        
        throw new IllegalArgumentException("Invalid Token: " + token);
    }

    private void updateToken(int primaryKey, String newToken) {
        dataStorageUpdate(this.getTableName(), Integer.toString(primaryKey), "token", newToken);
    }
    
    /**
     * Update a venues response to hosting
     *
     * @param responderName The name of the venue which responded
     * @param response The venues response
     * @return If the dataStorage successfully updated
     */
    public boolean updateResponse(String responderName, Response response){
        for(ResponderEntity responder : getAll()){
            if(responder.getName().equals(responderName)){
                return dataStorageUpdate(getTableName(), Integer.toString(responder.getPrimaryKey()), "response", response.getFriendlyName());
            }
        }
        
        return false;
    }
    
    /**
     * Update the date the venue has requested to host
     *
     * @param responderName The venue which has requested a date
     * @param date The date the venue requested
     * @return If the dataStorage was successfully updated
     */
    public boolean updateRequestedDate(String responderName, LocalDate date){
        for(ResponderEntity responder : getAll()){
            if(responder.getName().equals(responderName)){
                return dataStorageUpdate(getTableName(), Integer.toString(responder.getPrimaryKey()), "requestedHostingDate", Entity.dateFormat.format(date));
            }
        }
        
        return false;
    }
    
    public String getReceiptMessage(List<Meetup> meetups, ResponderEntity entity){
        Meetup meetup = new Meetup();
        
        for(Meetup mu : meetups){
            if(mu.getDate().equals(entity.getRequestedDate())){
                meetup = mu;
            }
        }
        
        return entity.getMessage(getReceipt(meetup, entity));
    }
    
    public static boolean isRequestedDateAvailable(Meetup meetup, ResponderEntity entity) {
        if(entity instanceof Venue) {
            return meetup.getDate().equals(entity.getRequestedDate()) && meetup.getVenue().equals("");
        } else {
            return meetup.getDate().equals(entity.getRequestedDate()) && meetup.getFood().equals("");
        }
    }
    
    private Receipt getReceipt(Meetup meetup, ResponderEntity entity){
        if(entity.getResponse().equals(Response.DECLINED)){
            return Receipt.NO;
        }
        else if(isRequestedDateAvailable(meetup, entity)){
            if(entity.getResponse().equals(Response.UNDECIDED)) {
                return Receipt.NOT_RESPONDED;
            } else {
                //if not hosting requested date and Response == Accepted then
                //assumes responder cancelled and SeaJUG volunteer removed them
                //from meetup and then changes responderEntity.response to reflect this
                boolean success = updateResponse(entity.getName(), Response.DECLINED);
                if(success){
                    return Receipt.NO;
                }
                else{
                    throw new IllegalArgumentException("Unable to update response");
                }
            }
        }
        else if(hostingRequestedDate(meetup, entity)){
            return Receipt.ACCEPTED;
        }
        else {
            return Receipt.ALREADY_TAKEN;
        }
    }
    
    private boolean hostingRequestedDate(Meetup meetup, ResponderEntity entity){
        if(entity instanceof Venue) {
            return meetup.getDate().equals(entity.getRequestedDate()) && meetup.getVenue().equals(entity.getName());
        } else {
            return meetup.getDate().equals(entity.getRequestedDate()) && meetup.getFood().equals(entity.getName());
        }
    }

    @Override
    public void update(ResponderEntity observable) {
        updateToken(observable.getPrimaryKey(),observable.getToken());
    }
}
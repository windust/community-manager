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
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.entities.Entity;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * The FoodSponsorCollection stores a collection of food sponsors and connect the list to a DataStorage.
 *
 * @author Crean 4 UR Coffee
 * @version 0.1
 */

@Repository(value = "food")
public class FoodSponsorCollection<T extends FoodSponsor> extends ResponderCollection<T>{

    /**
     * FoodSponsorCollection constructor with a call to the
     * super constructor in the ResponderCollection
     */
    public FoodSponsorCollection(){
        super("foodSponsors");
    }

    /**
     * FoodSponsorCollection constructor  with a String tableName
     * parameter passed to the constructor and a call super constructor
     * in ResponderCollection.
     * @param tableName String parameter for the tableName
     */
    public FoodSponsorCollection(String tableName){
        super(tableName);
    }


    /**
     *
     * @param dataStorage
     */
    public FoodSponsorCollection(DataStorage dataStorage){
        super(dataStorage, "foodSponsors");
    }

    /**
     *
     * @param dataStorage
     * @param tableName
     */
    public FoodSponsorCollection(DataStorage dataStorage, String tableName){
        super(dataStorage, tableName);
    }


    @Override
    public FoodSponsorCollection fetchFromDataStorage() {
        try{
            FoodSponsorCollection foodSponsorCollection = new FoodSponsorCollection(this.getDataStorage());

            for(Map<String, String> foodFields : getDataStorage().readAll(getTableName())){
                FoodSponsor foodSponsor = new FoodSponsor();
                try{
                    foodSponsor.build(foodFields);
                }
                catch (UnexpectedPrimaryKeyException e){
                    e.printStackTrace();
                }
                foodSponsorCollection.addToCollection(foodSponsor);
            }
            
            return foodSponsorCollection;
        } catch (IOException e){
            System.out.println("Error: Reading from non-existant table.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates food sponsor response to provide food
     *
     * @param foodSponsorName the name of food sponsor that responded
     * @param response the response from the food sponsor
     * @return if dataStorage successfully updated
     */
    public boolean updateResponse(String foodSponsorName, Response response){
        for(T foodSponsor : getAll()){
            if(foodSponsor.getName().equals(foodSponsorName)){
                return dataStorageUpdate(getTableName(), Integer.toString(foodSponsor.getPrimaryKey()), "response", response.getFriendlyName());
            }
        }

        return false;
    }

    /**
     * Updates food sponsor response to provide food
     *
     * @param foodSponsorName the name of food sponsor that responded
     * @param response the response from the food sponsor
     * @return if dataStorage successfully updated
     */
    public boolean updateFoodResponse(String foodSponsorName, Response response){
        return updateResponse(foodSponsorName,response);
    }

    /**
     * Updates the date the food sponsor would like to provide food
     *
     * @param foodSponsorName the name of the food sponsor that responded
     * @param date the date the food sponsor is providing food
     * @return if the dataStorage successfully updated
     */
    public boolean updateRequestedDate(String foodSponsorName, LocalDate date){
        for(FoodSponsor foodSponsor : getAll()){
            if(foodSponsor.getName().equals(foodSponsorName)){
                return dataStorageUpdate(getTableName(), Integer.toString(foodSponsor.getPrimaryKey()), "requestedDate", Entity.dateFormat.format(date));
            }
        }

        return false;
    }
}
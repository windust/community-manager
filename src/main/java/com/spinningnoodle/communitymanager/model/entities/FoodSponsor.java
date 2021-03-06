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
import java.util.Map;

/**
 * Food Entity
 *
 * Food stores all information associated with the food sponsor in the application.
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class FoodSponsor extends ResponderEntity{
    private String address;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String contactAltPhone;


    @Override
    public FoodSponsor build(Map<String, String> fields) {
        this.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
        this.setName(fields.getOrDefault("name", null));
        this.setAddress(fields.getOrDefault("address", null));
        this.setContactPerson(fields.getOrDefault("contactPerson", null));
        this.setContactEmail(fields.getOrDefault("contactEmail", null));
        this.setContactPhone(fields.getOrDefault("contactPhone", null));
        this.setContactAltPhone(fields.getOrDefault("contactAltPhone", null));
        this.setRequestedDate(convertDate(fields.getOrDefault("requestedDate", null)));
        this.setResponse(convertResponse(fields.getOrDefault("response", "")));
        this.setToken(fields.getOrDefault("token", null));
        this.messages = generateMessages();

        return this;
    }

    /**
     * Creates a new Food sponsor with a unique generated object ID
     */
    public FoodSponsor(){
        super();
    }

    /**
     * FoodSponsor constructor with the parameter int
     * primaryKey and a call to the superConstructor.
     * @param primaryKey
     */
    public FoodSponsor(int primaryKey){
        super(primaryKey);
    }

    /**
     * Creates a new FoodSponsor with all fields and unique generated ID
     *
     * @param primaryKey the key of the Food sponsor stored in the Database
     * @param name the name of the Food sponsor
     * @param address the address of the Food sponsor
     * @param contactPerson the name of the contact
     * @param contactEmail the contacts email
     * @param contactPhone the contacts phone number
     * @param contactAltPhone the contacts alternate phone number
     * @param requestedFoodDate the date this Food sponsor has offered to provide food
     */
    public FoodSponsor(int primaryKey, String name, String address, String contactPerson, String contactEmail,
    String contactPhone, String contactAltPhone, LocalDate requestedFoodDate){
        this.setPrimaryKey(primaryKey);
        setName(name);
        this.address = address;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactAltPhone = contactAltPhone;
        setRequestedDate(requestedFoodDate);
    }

    /**
     *
     * @return This food sponsor's address
     */
    public String getAddress(){
        return address;
    }

    /**
     *
     * @param address this food sponsor's address
     */
    public void setAddress(String address){
        this.address = address;
    }


    /**
     *
     * @return this contacts name
     */
    public String getContactPerson(){
        return contactPerson;
    }

    /**
     *
     * @param contactPerson this contacts name
     */
    public void setContactPerson(String contactPerson){
        this.contactPerson = contactPerson;
    }

    /**
     *
     * @return this contacts email
     */
    public String getContactEmail(){
        return contactEmail;
    }

    /**
     *
     * @param contactEmail this contacts email
     */
    public void setContactEmail(String contactEmail){
        this.contactEmail = contactEmail;
    }

    /**
     *
     * @return this contacts phone number
     */
    public String getContactPhone(){
        return contactPhone;
    }

    /**
     *
     * @param contactPhone this contacts phone number
     */
    public void setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
    }

    /**
     *
     * @return this contacts alternate phone number
     */
    public String getContactAltPhone(){
        return contactAltPhone;
    }

    /**
     *
     * @param contactAltPhone this contacts alternate phone number
     */
    public void setContactAltPhone(String contactAltPhone){
        this.contactAltPhone = contactAltPhone;
    }
    
    @Override
    protected Map<Receipt, String> generateMessages(){
        Map<Receipt, String> messages = super.generateMessages();
        String date;
        if(getRequestedDate() == null){
            date = "";
        } else {
            date = getRequestedDate().format(dateFormat);
        }
        
        messages.put(Receipt.NOT_RESPONDED, "Can you provide food on " + date + "?");
        messages.put(Receipt.ALREADY_TAKEN, "Thank you for volunteering but " + date + " is already being provided food by another sponsor.");
        messages.put(Receipt.ACCEPTED, "Thank you for providing food on " + date + ", Contact your SeaJUG contact to cancel.");
        
        return messages;
    }

    /**
     *
     * @return this contacts response to food request
     */
    public Response getFoodResponse(){
        return super.getResponse();
    }

    /**
     *
     * @param foodResponse this contacts response to request to sponsor food.
     */
    public void setFoodResponse(Response foodResponse){
        super.setResponse(foodResponse);
    }

    @Override
    public String toString() {
        return "FoodSponsor{" +
            "name='" + getName() + '\'' +
            ", address='" + address + '\'' +
            ", contactPerson='" + contactPerson + '\'' +
            ", contactEmail='" + contactEmail + '\'' +
            ", contactPhone='" + contactPhone + '\'' +
            ", contactAltPhone='" + contactAltPhone + '\'' +
            ", requestedFoodDate=" + getRequestedDate() +
            ", response='" + getToken() + '\'' +
            '}';
    }
}

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
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Venue Entity
 *
 * Venue stores all information associated with a venue in the application.
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class Venue extends ResponderEntity {
    private String address;
    private int capacity;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String contactAltPhone;
    private Response foodResponse;

    @Override
    public Venue build(Map<String, String> fields) throws UnexpectedPrimaryKeyException {
        //TODO find out how many of these fields can be abstracted to reduce redundant code
    	this.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
        this.setName(fields.getOrDefault("name", null));
        this.setAddress(fields.getOrDefault("address", null));
        this.setCapacity(Integer.parseInt(fields.getOrDefault("capacity", "0")));
        this.setContactPerson(fields.getOrDefault("contactPerson", null));
        this.setContactEmail(fields.getOrDefault("contactEmail", null));
        this.setContactPhone(fields.getOrDefault("contactPhone", null));
        this.setContactAltPhone(fields.getOrDefault("contactAltPhone", null));
        this.setRequestedDate(convertDate(fields.getOrDefault("requestedDate", null)));
        this.setResponse(convertResponse(fields.getOrDefault("response", "")));
        this.setFoodResponse(convertResponse(fields.getOrDefault("foodResponse", "")));
        this.setToken(fields.getOrDefault("token", ""));
        this.messages = generateMessages();

        return this;
    }

	/**
	 * Create a new Venue with a unique generated object ID
	 */
    public Venue() {
        super();
    }

    public Venue(int primaryKey) {
        super(primaryKey);
    }

	/**
	 * Create a new venue with all fields and a unique generated ID
	 *
	 * @param primaryKey The key of the venue stored in the database
	 * @param name The name of the venue (company name)
	 * @param address The address of the venue
	 * @param capacity The amount of people the venue is able to hold
	 * @param contactPerson The name of the person to contact for the venue
	 * @param contactEmail The email the venue can be reached at
	 * @param contactPhone The phone number the venue can be reached at
	 * @param contactAltPhone An alternate phone number which could be used to contact the venue
	 * @param requestedDate The date this venue has requested to host
     * @param response This venues response to the requested date
     * @param foodResponse This venue's response to also providing food
     * @param token The token of the venue
	 */
    public Venue(int primaryKey, String name, String address, int capacity, String contactPerson,
        String contactEmail, String contactPhone, String contactAltPhone,
        LocalDate requestedDate, String response, String foodResponse, String token) {
        this.setPrimaryKey(primaryKey);
        setName(name);
        this.address = address;
        this.capacity = capacity;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactAltPhone = contactAltPhone;
        this.setRequestedDate(requestedDate);
        this.setResponse(convertResponse(response));
        this.setFoodResponse(convertResponse(foodResponse));
        this.setToken(token);
    }

	/**
	 * @return This venue's address
	 */
    public String getAddress() {
        return address;
    }

	/**
	 * @param address This venue's address
	 */
    public void setAddress(String address) {
        this.address = address;
    }

	/**
	 * @return The amount of people this venue can hold
	 */
    public int getCapacity() {
        return capacity;
    }

	/**
	 * @param capacity The amount of people this venue can hold
	 */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

	/**
	 * @return The name of the person to contact for the venue
	 */
    public String getContactPerson() {
        return contactPerson;
    }

	/**
	 * @param contactPerson The name of the person to contact for the venue
	 *
	 */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

	/**
	 * @return The email the venue can be reached at
	 */
    public String getContactEmail() {
        return contactEmail;
    }

	/**
	 * @param contactEmail The email the venue can be reached at
	 */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

	/**
	 * @return The phone number the venue can be reached at
	 */
    public String getContactPhone() {
        return contactPhone;
    }

	/**
	 * @param contactPhone The phone number the venue can be reached at
	 */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

	/**
	 * @return An alternate phone number which could be used to contact the venue
	 */
    public String getContactAltPhone() {
        return contactAltPhone;
    }

	/**
	 * @param contactAltPhone An alternate phone number which could be used to contact the venue
	 */
    public void setContactAltPhone(String contactAltPhone) {
        this.contactAltPhone = contactAltPhone;
    }

    public Response getFoodResponse(){
        return foodResponse;
    }

    public void setFoodResponse(Response response){
        this.foodResponse = foodResponse;
    }
    
    @Override
    protected Map<Receipt, String> generateMessages(){
        Map<Receipt, String> messages = new HashMap<>();
        String date;
        
        /*
         *TODO currently used to prevent NullPointerExceptions thrown during
         * fetchFromDataStorage caused by other entities, find better alternative
         */
        if(getRequestedDate() == null){
            date = "";
        } else {
            date = getRequestedDate().format(dateFormat);
        }
        
        messages.put(Receipt.NO, "Thank you for your consideration.");
        messages.put(Receipt.NOT_RESPONDED, "Can you host on " + date + "?");
        messages.put(Receipt.ALREADY_TAKEN, "Thank you for volunteering but " + date + " is already being hosted by another venue.");
        messages.put(Receipt.ACCEPTED, "Thank you for hosting on " + date + ", Contact your SeaJUG contact to cancel.");
        messages.put(Receipt.ACCEPTED_PLUS, "Thank you for hosting and providing food on " + date + ", Contact your SeaJUG contact to cancel.");
        
        return messages;
    }

    @Override
	public String toString() {
		return "Venue{" +
			", primaryKey=" + this.getPrimaryKey() +
			", name='" + getName() + '\'' +
			", address='" + address + '\'' +
			", capacity=" + capacity +
			", contactPerson='" + contactPerson + '\'' +
			", contactEmail='" + contactEmail + '\'' +
			", contactPhone='" + contactPhone + '\'' +
			", contactAltPhone='" + contactAltPhone + '\'' +
			", requestedHostingDate='" + getRequestedDate() + '\'' +
            ", response='" + getResponse() + '\'' +
            ", foodResponse='" + getFoodResponse() + '\'' +
            ", token='" + getToken() + '\''+
			'}';
	}
}

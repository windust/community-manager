package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.model.entities.Admin;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.List;

public interface DataManager {

    /**
     * Method to verify admin before they can access the
     * website.
     * @param email String
     * @return boolean
     */
    boolean verifyAdmin(String email);

    /**
     * Get's a list of all admins.
     * @return list of all admins.
     */
    List<Admin> getAllAdmins();

    /**
     * Get's a list of all meetups.
     * @return list of all meetups.
     */
    List<Meetup> getAllMeetups();

    /**
     * Get's a list of all hosted meetups.
     * @return list of all hosted meetups.
     */
    List<Meetup> getAllHostedMeetups();

    /**
     * Get's venue by token.
     * @param venueToken String
     * @return venues token
     */
    Venue getVenueByToken(String venueToken);

    /**
     * Get's food by token.
     * @param foodToken String
     * @return foods token
     */
    FoodSponsor getFoodByToken(String foodToken);

    /**
     * Set's venue for meetup.
     * @param venueName String
     * @param requestedDate String
     * @param dateRequestedByAdmin LocalDate
     * @return boolean for venue being set.
     */
    boolean setVenueForMeetup(String venueName, String requestedDate, LocalDate dateRequestedByAdmin);

    /**
     * Set's the venue food for the meetup.
     * @param venueName String
     * @param requestedDate String
     * @param dateRequestedByAdmin LocalDate
     * @return boolean for food being set
     */
    boolean setVenueFoodForMeetup(String venueName, String requestedDate,
        LocalDate dateRequestedByAdmin);

    /**
     * Set's food for meetup.
     * @param foodName String
     * @param requestedDate String
     * @param dateRequestedByAdmin LocalDate
     * @return boolean for food being set
     */
    boolean setFoodForMeetup(String foodName, String requestedDate, LocalDate dateRequestedByAdmin);

    /**
     * Get's a list of all the venues
     * @return a list of all the venues
     */
    List<Venue> getAllVenues();

    /**
     * Request a host for a specific date
     * @param primaryKey int
     * @param date LocalDate
     * @return String
     */
    String requestHost(String primaryKey, LocalDate date);

    /**
     * Request a food sponsor.
     * @param primaryKey int
     * @param date LocalDate
     * @return String
     */
    String requestFood(String primaryKey, LocalDate date);

    /**
     * Get's the Database Access page
     * @return String of the Database
     */
    String getDatabaseAccessPage();

    /**
     * Get's a list of all the food sponsors
     * @param meetupKey Meetup
     * @return list of all the food sponsors
     */
    List<FoodSponsor> getAllFoodSponsors(Meetup meetupKey);

    /**
     * Get's messages
     * @param entity Responder Entity.
     * @return string of message. 
     */
    String getMessage(ResponderEntity entity);
}

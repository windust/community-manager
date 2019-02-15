package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.entities.Meetup;

import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetupCollection extends ICollection<Meetup> {
	private static final String TABLE_NAME = "meetups";

	public MeetupCollection(DataStorage dataStorage) {
		super(dataStorage);
	}

	@Override
	public void fetchFromDataStorage() {
		this.clear();
		try {
			for(Map<String, String> meetupFields : dataStorage.readAll(TABLE_NAME)) {
				Meetup meetup = new Meetup();

				meetup.build(meetupFields);
				this.entities.put(meetup.getMeetupId(), meetup);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	public boolean updateMeetupHost(Meetup meetup, String nameOfVenue){
	    return true;
    }

	@Override
	public void update(Meetup observable) {

	}

	// set the venue for a hosting date
	public boolean setVenueForMeetup(String venueName, String hostingDate) {
		for(Meetup meetup : entities.values()) {
			if(meetup.getDate().equals(hostingDate)) {
				return dataStorage.update(TABLE_NAME, Integer.toString(meetup.getPrimaryKey()), "venue", venueName);
			}
		}
		return false;
	}

	// get all meetups with date, speaker, and venue
	public List<Map<String, String>> getAllMeetupsForToken(String token) {
		// list to store the meetups
		List<Map<String, String>> meetups = new ArrayList<>();
		// add the venue with the token to [0] index of list
		meetups.add(isTokenValid(token));

		for(Meetup meetup : entities.values()) {
			Map<String, String> meetupInfo = new HashMap<>();
			meetupInfo.put("date", meetup.getDate());
			meetupInfo.put("speaker", meetup.getSpeaker());
			meetupInfo.put("venue", meetup.getVenue());
			meetups.add(meetupInfo);
		}

		return meetups;
	}

	// is valid token, get name of venue & requested date
	// is value 'excellent' not valid
	private Map<String, String> isTokenValid(String token) {
		String stuff = "";
		// create venue collection with this datastorage
		VenueCollection venueCollection = new VenueCollection(this.dataStorage);
//		venueCollection.fetchFromDataStorage();

		// Loop through venue collection until a venue is found to have the token
		for(Venue venue : venueCollection.getAll()) {
			// If the venue is found to have the token return the venue info as a map
			if(venue.getToken().equals(token)) {
				stuff += " - " + venue.getToken();
				Map<String, String> venueInfo = new HashMap<>();
				venueInfo.put("name", venue.getName());
				venueInfo.put("requestedDate", venue.getRequestedHostingDate());

				return venueInfo;
			}
		}

		// If the for loop didnt find a the venue by token
		throw new IllegalArgumentException("Token: " + token + " does not exist for any venue." + stuff);
	}
}

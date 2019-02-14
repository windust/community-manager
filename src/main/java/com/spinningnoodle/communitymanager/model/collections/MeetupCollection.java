package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetupCollection extends ICollection<Meetup> {
	private static final String TABLE_NAME = "venues";

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

	public List<Map<String, String>> getMeetupsByVenueToken(String token) {
		List<Map<String, String>> meetupInfo = new ArrayList<>();
		for(Meetup meetup : this.entities.values()) {
			if(meetup.getVenue().getToken().equals(token)) {
				Map<String, String> venue = new HashMap<>();
				venue.put("name", meetup.getVenue().getName());
				venue.put("requested_date", meetup.getVenue().getRequestedHostingDate());

				meetupInfo.add(venue);

				Map<String, String> meetups = new HashMap<>();
				meetups.put("date", meetup.getDate());
				meetups.put("speaker", meetup.getSpeaker());
				meetups.put("venue", meetup.getVenue().getName());

				meetupInfo.add(meetups);
			}
		}

		return meetupInfo;
	}
}

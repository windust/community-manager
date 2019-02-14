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
	private static Map<Integer, Meetup> meetups = new HashMap<>();

	public MeetupCollection(DataStorage dataStorage) {
		super(dataStorage);
	}

	@Override
	public void fetchFromDataStorage() {
		meetups.clear();
		try {
			for(Map<String, String> meetupFields : dataStorage.readAll(TABLE_NAME)) {
				Meetup meetup = new Meetup();

				meetup.build(meetupFields);
				meetups.put(meetup.getMeetupId(), meetup);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addToCollection(Meetup meetup) {
		meetups.put(meetup.getMeetupId(), meetup);
	}

	@Override
	public Meetup getById(int meetupId) throws EntityNotFoundException {
		if(!meetups.containsKey(meetupId)) {
			throw new EntityNotFoundException();
		}
		return meetups.get(meetupId);
	}

	@Override
	public List<Meetup> getAll() {
		return new ArrayList<>(meetups.values());
	}

	@Override
	public int size() {
		return meetups.size();
	}

	@Override
	public void clear() {
		meetups.clear();
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
}

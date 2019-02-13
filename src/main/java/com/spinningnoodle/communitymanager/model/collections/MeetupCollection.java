package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetupCollection implements ICollection<Meetup> {
	private static final String TABLE_NAME = "venues";
	static Map<Integer, Meetup> meetup = new HashMap<>();


	@Override
	public void fetchFromDataStorage(DataStorage dataStorage) {

	}

	@Override
	public void addToCollection(Meetup entity) {

	}

	@Override
	public Meetup getById(int venueId) throws EntityNotFoundException {
		return null;
	}

	@Override
	public List<Meetup> getAll() {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public void clear() {

	}
}

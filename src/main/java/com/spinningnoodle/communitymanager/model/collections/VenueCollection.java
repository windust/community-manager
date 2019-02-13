package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The VenueCollection stores a collection of venues and connect the list to a DataStorage.
 *
 * @author Crean 4 UR Coffee
 * @version 0.1
 */
public class VenueCollection implements ICollection<Venue> {
	private static final String TABLE_NAME = "venues";
	static Map<Integer, Venue> venues = new HashMap<>();

	@Override
	public void fetchFromDataStorage(DataStorage dataStorage) {
		venues.clear();
		try {
			for(Map<String, String> venueFields : dataStorage.readAll(TABLE_NAME)) {
				Venue venue = new Venue();
				try {
					venue.build(venueFields);
				} catch (UnexpectedPrimaryKeyException e) {
					e.printStackTrace();
				}
				venues.put(venue.getVenueId(), venue);
			}
		} catch (IOException e) {
			System.out.println("Error: Reading from non-existant table.");
			e.printStackTrace();
		}
	}

	@Override
	public void addToCollection(Venue venue) {
		venues.put(venue.getVenueId(), venue);
	}

	@Override
	public Venue getById(int venueId) throws EntityNotFoundException {
		if(!venues.containsKey(venueId)) {
			throw new EntityNotFoundException();
		}
		return venues.get(venueId);
	}

	@Override
	public List<Venue> getAll() {
		return new ArrayList<>(venues.values());
	}


	@Override
	public int size() {
		return venues.size();
	}

	@Override
	public void clear() {
		venues.clear();
	}
}

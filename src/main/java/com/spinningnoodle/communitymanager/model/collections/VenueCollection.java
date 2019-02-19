package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.entities.Venue;

import java.io.IOException;
import java.util.Map;

/**
 * The VenueCollection stores a collection of venues and connect the list to a DataStorage.
 *
 * @author Crean 4 UR Coffee
 * @version 0.1
 */
public class VenueCollection extends EntityCollection<Venue> {
	private static final String TABLE_NAME = "venues";

	public VenueCollection(DataStorage dataStorage) {
		super(dataStorage);
	}

	@Override
	public void fetchFromDataStorage() {
		this.clear();
		try {
			for(Map<String, String> venueFields : getDataStorage().readAll(TABLE_NAME)) {
				Venue venue = new Venue();
				try {
					venue.build(venueFields);
				} catch (UnexpectedPrimaryKeyException e) {
					e.printStackTrace();
				}
				addToEntities(venue);
			}
		} catch (IOException e) {
			System.out.println("Error: Reading from non-existant table.");
			e.printStackTrace();
		}
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public void update(Venue observable) {

	}

	/*
	public boolean setVenueRequestedHostingDateByVenueName(String venueName, String requestedHostingDate) {
		for(Venue venue : this.entities.values()) {
			if(venue.getName().equals(venueName)) {
				return dataStorage.update(TABLE_NAME, Integer.toString(venue.getPrimaryKey()), "requestedHostingDate", requestedHostingDate);
			}
		}
		return false;
	}
	*/
}
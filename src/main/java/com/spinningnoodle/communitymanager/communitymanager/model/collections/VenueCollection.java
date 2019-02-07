package com.spinningnoodle.communitymanager.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.communitymanager.model.entities.Venue;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
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
public class VenueCollection {
	private static final String TABLE_NAME = "venues";
	private static Map<Integer, Venue> venues = new HashMap<>();

	/**
	 * Gets all venues from a DataStorage.
	 *
	 * @param dataStorage The DataStorage used to store venues.
	 */
	public static void fetchFromDataStorage(DataStorage dataStorage) {
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

	/**
	 * Add one venue to the collection.
	 *
	 * @param venue The Venue to be saved into the Collection .
	 */
	public static void addToCollection(Venue venue) {
		venues.put(venue.getVenueId(), venue);
	}

	/**
	 * Gets a venue from this collection based on it's Venue ID
	 *
	 * @param venueId The venueID of the venue to be retrieves.
	 * @return A venue who's venueId matches the venueId passed in
	 * @throws EntityNotFoundException When venue with the Id passed cannot be found
	 */
	public static Venue getById(int venueId) throws EntityNotFoundException {
		if(!venues.containsKey(venueId)) {
			throw new EntityNotFoundException();
		}
		return venues.get(venueId);
	}

	/**
	 * Gets all Venues.
	 *
	 * @return A List of all venues.
	 */
	public static List<Venue> getAll() {
		return new ArrayList<>(venues.values());
	}

	/**
	 * Get the amount of Venues stored in the collection
	 *
	 * @return The amount of Venues stored in the collection
	 */
	public static int size() {
		return venues.size();
	}

	/**
	 * Removes all Venues from the collection
	 */
	public static void clear() {
		venues.clear();
	}
}

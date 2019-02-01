package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The VenueCollection stores a collection of venues and connect the list to a DataStorage.
 *
 * @author Quentin Guenther
 * @version 1.0
 */
public abstract class VenueCollection {
	private static Map<Integer, Venue> venues = new HashMap<>();

	/**
	 * Gets all venues from a DataStorage.
	 *
	 * @param dataStorage The DataStorage used to store venues.
	 */
	public static void fetchFromDataStorage(DataStorage dataStorage) {

	}

	/**
	 * Updates the DataStore with the updated venue.
	 *
	 * @param venue the venue which has been updated
	 * @param dataStorage The DataStorage where the venue exists in memory.
	 */
	public static void updateVenue(Venue venue, DataStorage dataStorage) {

	}

	/**
	 * Gets a venue from this collection based on it's Venue ID
	 *
	 * @param venueId The venueID of the venue to be retrieves.
	 * @return A venue who's venueId matches the venueId passed in
	 */
	public static Venue getById(int venueId) {
		return null;
	}

	/**
	 * Gets all Venues.
	 *
	 * @return A List of all venues.
	 */
	public static List<Venue> getAll() {
		return null;
	}
}

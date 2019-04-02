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
import com.spinningnoodle.communitymanager.exceptions.AttributeException;
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import java.util.Map;

/**
 * Meetup Entity
 *
 * Meetup stores all information associated with a meetup in the application
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class Meetup extends Entity {
	private static int nextId = 1;

	private int meetupId;
	private int primaryKey;
	private String date;
	private String speaker;
	private String topic;
	private String description;
	private String venue;
	private String food;
	private String after;

    /**
     * Create a new Meetup with a unique generated object ID
     */
	public Meetup() {
		setMeetupId();
	}

    /**
     * Create a new Meetup with all fields and a unique generated ID
     *
     * @param primaryKey The key of the venue stored in the database
     * @param date The date the meetup will be hosted
     * @param speaker The speaker at the venue
     * @param topic The topic of the meetup
     * @param description The description of the meetup
     * @param venue The venue which will host the venue
     */
	public Meetup(int primaryKey, String date, String speaker, String topic,
		String description, String venue, String food, String after) {
		this();
		this.primaryKey = primaryKey;
		this.date = date;
		this.speaker = speaker;
		this.topic = topic;
		this.description = description;
		this.venue = venue;
		this.food = food;
		this.after = after;
	}

	@Override
	public Entity build(Map<String, String> fields) throws AttributeException {
		this.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
		this.setDate(fields.getOrDefault("date", null));
		this.setSpeaker(fields.getOrDefault("speaker", null));
		this.setTopic(fields.getOrDefault("topic", null));
		this.setDescription(fields.getOrDefault("description", null));
		this.setVenue(fields.getOrDefault("venue", null));
		this.setFood(fields.getOrDefault("food", null));
		this.setAfter(fields.getOrDefault("after", null));

		return this;
	}

	public static int getNextId() {
		return nextId;
	}

    /**
     * @return This meetup's unique ID. NOT ASSOCIATED WITH PRIMARY KEY
     */
	public int getMeetupId() {
		return meetupId;
	}

    /**
     * Increments the Meetup's ID to the next available ID. NOT ASSOCIATED WITH PRIMARY KEY
     */
	public void setMeetupId() {
		this.meetupId = nextId;
		++nextId;
	}

    /**
     * @return The meetup's primary key stored in DataStorage
     */
	public int getPrimaryKey() {
		return primaryKey;
	}

    /**
     * @param primaryKey The venue's primary key stored in DataStorage
     * @throws UnexpectedPrimaryKeyException If Primary Key is 0 or < -1
     */
	public void setPrimaryKey(int primaryKey) {
		if(primaryKey == 0 || primaryKey < -1) {
			throw new UnexpectedPrimaryKeyException();
		}

		this.primaryKey = primaryKey;
	}

    /**
     * @return The date the meetup will take place
     */
	public String getDate() {
		return date;
	}

    /**
     * @param date The date the meetup will take place
     */
	public void setDate(String date) {
		this.date = date;
	}

    /**
     * @return The speaker at the meetup
     */
	public String getSpeaker() {
		return speaker;
	}

    /**
     * @param speaker The speaker at the meetup
     */
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

    /**
     * @return The topic of the meetup
     */
	public String getTopic() {
		return topic;
	}

    /**
     * @param topic The topic of the meetup
     */
	public void setTopic(String topic) {
		this.topic = topic;
	}

    /**
     * @return The description of the meetup
     */
	public String getDescription() {
		return description;
	}

    /**
     * @param description The description of the meet up
     */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * @return The venue which will host the meetup
     */
	public String getVenue() {
		return venue;
	}

    /**
     * @param venue The venue which will host the meetup
     */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	@Override
    public String toString() {
        return "Meetup{" +
            "meetupId=" + meetupId +
            ", primaryKey=" + primaryKey +
            ", date='" + date + '\'' +
            ", speaker='" + speaker + '\'' +
            ", topic='" + topic + '\'' +
            ", description='" + description + '\'' +
            ", venue=" + venue +
            '}';
    }
}

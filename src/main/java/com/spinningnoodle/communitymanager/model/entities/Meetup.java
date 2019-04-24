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
import java.time.LocalDate;
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
	private LocalDate date;
	private String speaker;
	private String topic;
	private String description;
	private String venue;
	private String food;
	private String after;

	public Meetup(){}

	public Meetup(int primaryKey) {
		super(primaryKey);
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
	public Meetup(int primaryKey, LocalDate date, String speaker, String topic,
		String description, String venue, String food, String after) {
		super(primaryKey);
		this.date = date;
		this.speaker = speaker;
		this.topic = topic;
		this.description = description;
		this.venue = venue;
		this.food = food;
		this.after = after;
	}

	@Override
	public Meetup build(Map<String, String> fields) throws AttributeException {
		this.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
		this.setDate(convertDate(fields.getOrDefault("date", null)));
		this.setSpeaker(fields.getOrDefault("speaker", null));
		this.setTopic(fields.getOrDefault("topic", null));
		this.setDescription(fields.getOrDefault("description", null));
		this.setVenue(fields.getOrDefault("venue", null));
		this.setFood(fields.getOrDefault("food", null));
		this.setAfter(fields.getOrDefault("after", null));

		return this;
	}

    /**
     * @return The date the meetup will take place
     */
	public LocalDate getDate() {
		return date;
	}

    /**
     * @param date The date the meetup will take place
     */
	public void setDate(LocalDate date) {
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
            ", primaryKey=" + this.getPrimaryKey() +
            ", date='" + date + '\'' +
            ", speaker='" + speaker + '\'' +
            ", topic='" + topic + '\'' +
            ", description='" + description + '\'' +
            ", venue=" + venue +
            '}';
    }
}

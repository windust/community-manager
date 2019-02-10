package com.spinningnoodle.communitymanager.model.entities;

import com.spinningnoodle.communitymanager.exceptions.AttributeException;
import java.util.Map;

public class Meetup implements IEntity {
	private static int nextId = 1;

	private int meetupId;
	private int primaryKey;
	private String date;
	private String speaker;
	private String topic;
	private String description;
	private Venue venue;

	@Override
	public IEntity build(Map<String, String> fields) throws AttributeException {
		return null;
	}

	public static int getNextId() {
		return nextId;
	}

	public static void setNextId(int nextId) {
		Meetup.nextId = nextId;
	}

	public int getMeetupId() {
		return meetupId;
	}

	public void setMeetupId(int meetupId) {
		this.meetupId = meetupId;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}
}

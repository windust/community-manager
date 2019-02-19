package com.spinningnoodle.communitymanager.model.entities;

import com.spinningnoodle.communitymanager.exceptions.AttributeException;
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import java.util.Map;

public class Meetup extends IEntity {
	private static int nextId = 1;

	private int meetupId;
	private int primaryKey;
	private String date;
	private String speaker;
	private String topic;
	private String description;
	private String venue;

	public Meetup() {
		setMeetupId();
	}

	public Meetup(int primaryKey, String date, String speaker, String topic,
		String description, String venue) {
		this();
		this.primaryKey = primaryKey;
		this.date = date;
		this.speaker = speaker;
		this.topic = topic;
		this.description = description;
		this.venue = venue;
	}

	@Override
	public IEntity build(Map<String, String> fields) throws AttributeException {
		this.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
		this.setDate(fields.getOrDefault("date", null));
		this.setSpeaker(fields.getOrDefault("speaker", null));
		this.setTopic(fields.getOrDefault("topic", null));
		this.setDescription(fields.getOrDefault("description", null));
		this.setVenue(fields.getOrDefault("venue", null));

		return this;
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

	public void setMeetupId() {
		this.meetupId = nextId;
		++nextId;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		if(primaryKey == 0 || primaryKey < -1) {
			throw new UnexpectedPrimaryKeyException();
		}

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

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}
}

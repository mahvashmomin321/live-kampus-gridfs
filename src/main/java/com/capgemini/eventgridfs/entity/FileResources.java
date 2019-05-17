package com.capgemini.eventgridfs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FileResources {
	private String id;
	private String eventName;
	private String eventVenue;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private String eventDate;
	private String eventhostedBy;

	public FileResources() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public FileResources(String id, String eventName, String eventVenue, String eventDate, String eventhostedBy) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.eventVenue = eventVenue;
		this.eventDate = eventDate;
		this.eventhostedBy = eventhostedBy;
	}



	public String getEventVenue() {
		return eventVenue;
	}



	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}



	public String getEventDate() {
		return eventDate;
	}



	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}



	public String getEventhostedBy() {
		return eventhostedBy;
	}



	public void setEventhostedBy(String eventhostedBy) {
		this.eventhostedBy = eventhostedBy;
	}



	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

package com.test.kafkaConsumerJsonOffsetBased;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Student {
	String name;
	String id;
	String course;
	
	 @JsonCreator
	 public Student(@JsonProperty("name")String name, @JsonProperty("id")String id,@JsonProperty("course") String course) {
		this.name = name;
		this.id = id;
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	
	

}

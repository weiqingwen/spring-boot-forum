package com.qingwenwei.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer weight;
	private String name; // unique
	private String displayName;
	private String username; // user who created the category
	private Timestamp dateCreated;

	public Category() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", weight=" + weight + ", name=" + name + ", displayName=" + displayName
				+ ", username=" + username + ", dateCreated=" + dateCreated + "]";
	}

}
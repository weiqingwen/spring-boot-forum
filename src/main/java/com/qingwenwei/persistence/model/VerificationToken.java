package com.qingwenwei.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class VerificationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int A_DAY_IN_MILLIS = 24 * 60 * 60 * 1000; // a day in milliseconds

	private Long id;
	private String token;
	private User user;
	private Timestamp expiryDate;

	public VerificationToken() {

	}

	public VerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
		this.expiryDate = this.calculateExpiryDate();
	}

	private Timestamp calculateExpiryDate() {
		return new Timestamp(System.currentTimeMillis() + A_DAY_IN_MILLIS);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
}

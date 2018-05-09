package com.qingwenwei.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	// constants
	public static String USER = "USER";
	public static String ADMIN = "ADMIN";

	private Long id;
	private String username;
	private String password;
	private String email;
	private Long activated;
	private Timestamp dateCreated;
	private String avatarLocation;
	private String bio;
	private String roles;

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getActivated() {
		return activated;
	}

	public void setActivated(Long activated) {
		this.activated = activated;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getAvatarLocation() {
		return avatarLocation;
	}

	public void setAvatarLocation(String avatarLocation) {
		this.avatarLocation = avatarLocation;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Set<String> getRolesSet() {
		if (null == roles) {
			return null;
		}
		return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(getRoles().split(","))));
	}

	public void addRole(String role) {
		String currRoles = this.getRoles();
		if (null == currRoles || this.getRoles().contains(role)) {
			return;
		}
		this.setRoles(currRoles + "," + role);
	}

	public void activated(boolean activated) {
		this.setActivated(activated == true ? 1L : 0L);
	}

	public boolean isEnabled() {
		return this.activated == 1 ? true : false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", activated=" + (activated == 1 ? true : false) + ", dateCreated=" + dateCreated
				+ ", avatarLocation=" + avatarLocation + ", bio=" + bio + ", roles=" + roles + "]";
	}

}
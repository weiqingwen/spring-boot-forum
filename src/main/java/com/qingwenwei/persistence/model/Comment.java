package com.qingwenwei.persistence.model;

import java.sql.Timestamp;

public class Comment {

	private Long id;
	private Long postId;
	private String body;
	private User user;
	private Timestamp dateCreated;

	public Comment() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", postId=" + postId + ", body=" + body + ", user=" + user + ", dateCreated="
				+ dateCreated + "]";
	}

}
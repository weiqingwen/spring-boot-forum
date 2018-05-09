package com.qingwenwei.web.dto;

public class CommentDto {

	private String comment;
	private String username;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "NewCommentForm [comment=" + comment + ", username=" + username + "]";
	}

}

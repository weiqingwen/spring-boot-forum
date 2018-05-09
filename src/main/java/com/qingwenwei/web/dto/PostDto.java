package com.qingwenwei.web.dto;

public class PostDto {

	private Long postId;
	private String title;
	private String body;
	private String category;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "NewPostForm [postId=" + postId + ", title=" + title + ", body=" + body + ", category=" + category + "]";
	}

}

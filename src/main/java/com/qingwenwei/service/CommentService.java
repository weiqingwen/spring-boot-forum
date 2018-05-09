package com.qingwenwei.service;

import com.qingwenwei.persistence.model.Comment;
import com.qingwenwei.web.dto.CommentDto;

public interface CommentService {

	void save(Comment comment);

	int countNumCommentsByPostId(Long postId);

	Comment createNewCommentOnPost(Long postId, CommentDto newCommentForm);

}

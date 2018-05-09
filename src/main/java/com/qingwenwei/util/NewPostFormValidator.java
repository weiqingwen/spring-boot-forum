package com.qingwenwei.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.qingwenwei.persistence.model.Post;

@Component
public class NewPostFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Post.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Post newPost = (Post) object;

		// new post title validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
		if (newPost.getTitle() != null && !newPost.getTitle().isEmpty()) {
			if (newPost.getTitle().length() < 3 || newPost.getTitle().length() > 30) {
				errors.rejectValue("title", "Size.post.title");
			}
		}

		// new post body validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "NotEmpty");
		if (newPost.getBody() != null && !newPost.getBody().isEmpty()) {
			if (newPost.getBody().length() < 3 || newPost.getBody().length() > 100) {
				errors.rejectValue("body", "Size.post.body");
			}
		}

	}

}
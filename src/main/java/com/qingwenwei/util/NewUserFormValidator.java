package com.qingwenwei.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.qingwenwei.persistence.model.User;
import com.qingwenwei.service.UserService;
import com.qingwenwei.web.dto.UserRegistrationDto;

@Component
public class NewUserFormValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object object, Errors errors) {
		UserRegistrationDto userForm = (UserRegistrationDto) object;

		// username validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (userForm.getUsername() != null && !userForm.getUsername().isEmpty()) {
			if (userForm.getUsername().length() < 3 || userForm.getUsername().length() > 10) {
				errors.rejectValue("username", "Size.userForm.username");
			}
			if (null != userService.findByUsername(userForm.getUsername())) {
				errors.rejectValue("username", "Duplicate.userForm.username");
			}
		}

		// email validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		if (userForm.getEmail() != null && !userForm.getEmail().isEmpty()) {
			Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(userForm.getEmail());
			if (!matcher.matches()) {
				errors.rejectValue("email", "Invalid.userForm.email");
			}
			if (null != userService.findByEmail(userForm.getEmail())) {
				errors.rejectValue("email", "Duplicate.userForm.email");
			}
		}

		// password validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (userForm.getPassword() != null && !userForm.getPassword().isEmpty()) {
			if (userForm.getPassword().length() < 3 || userForm.getPassword().length() > 32) {
				errors.rejectValue("password", "Size.userForm.password");
			}
		}

		// password confirmation validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matchingPassword", "NotEmpty");
		if (userForm.getMatchingPassword() != null && !userForm.getMatchingPassword().isEmpty()) {
			if (userForm.getMatchingPassword().length() < 3 || userForm.getMatchingPassword().length() > 32) {
				errors.rejectValue("matchingPassword", "Size.userForm.password");
			}
			if (!userForm.getMatchingPassword().equals(userForm.getPassword())) {
				errors.rejectValue("matchingPassword", "Diff.userForm.matchingPassword");
			}
		}
	}
}
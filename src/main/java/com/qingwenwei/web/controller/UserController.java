package com.qingwenwei.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qingwenwei.exception.BadRequestException;
import com.qingwenwei.exception.ResourceNotFoundException;
import com.qingwenwei.service.UserService;
import com.qingwenwei.util.NewUserFormValidator;
import com.qingwenwei.web.dto.UserRegistrationDto;
import com.qingwenwei.web.dto.UserSettingsDto;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private NewUserFormValidator userValidator;

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public String showUserProfilePage(@RequestParam(value = "tab", required = false) String tabType,
			@PathVariable Long userId, Model model) {
		if (null == userId) {
			throw new BadRequestException("Path variable userId cound not be null.");
		}
		Map<String, Object> attributes = this.userService.getUserProfileAndPostsByUserIdByTabType(userId, tabType);
		if (null == attributes) {
			throw new ResourceNotFoundException("attributes not found.");
		}
		model.addAllAttributes(attributes);
		return "forum/user-profile";
	}

	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		model.addAttribute("userDto", new UserRegistrationDto());
		return "forum/user-registration";
	}

	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public String registerNewUser(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
			BindingResult bindingResult, Model model, HttpServletRequest request) {
		/*
		 * form validation, check username and email uniqueness
		 */
		this.userValidator.validate(userDto, bindingResult);
		if (bindingResult.hasErrors()) {
			logger.info("BindingResult has errors >> " + bindingResult.getFieldError());
			return "forum/user-registration";
		}
		Map<String, Object> attributes = this.userService.registerUserAccount(userDto, request);
		if (null == attributes) {
			throw new ResourceNotFoundException("attributes not found.");
		}
		model.addAllAttributes(attributes);
		return "forum/user-registration-result";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String displayLoginPage(Model model) {
		model.addAttribute("title", "用户登录");
		return "forum/user-login";
	}

	@RequestMapping(value = "/user/login-success", method = RequestMethod.GET)
	public String showAdminPage() {
		return "forum/user-login";
	}

	@RequestMapping(value = "/user/registration-confirm", method = RequestMethod.GET)
	public String confirmRegistration(@RequestParam("token") String token, Model model) {
		if (null == token || token.equals("")) {
			throw new BadRequestException("Invalid user registration confirmation token.");
		}
		Map<String, Object> attributes = this.userService.confirmUserRegistrationWithToken(token);
		if (null == attributes) {
			throw new ResourceNotFoundException("attributes not found.");
		}
		model.addAllAttributes(attributes);
		return "forum/user-registration-confirm";
	}

	@RequestMapping(value = "/user/settings", method = RequestMethod.GET)
	public String showUserSettingsPage(Model model) {
		Map<String, Object> attributes = this.userService.getUserSettingPage();
		if (null == attributes) {
			throw new ResourceNotFoundException("attributes not found.");
		}
		model.addAllAttributes(attributes);
		return "forum/user-settings";
	}

	@RequestMapping(value = "/user/settings", method = RequestMethod.POST)
	public String handleFileUpload(@ModelAttribute("userSettingsDto") UserSettingsDto userSettingsDto, Model model) {
		if (null == userSettingsDto) {
			throw new BadRequestException("UserSettingsDto cound not be null.");
		}
		Map<String, Object> attributes = this.userService.updateUserProfile(userSettingsDto);
		if (null == attributes) {
			throw new ResourceNotFoundException("attributes not found.");
		}
		model.addAllAttributes(attributes);
		return "forum/user-settings";
	}

}

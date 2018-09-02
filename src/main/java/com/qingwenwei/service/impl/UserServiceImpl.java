package com.qingwenwei.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qingwenwei.event.OnRegistrationCompleteEvent;
import com.qingwenwei.persistence.dao.CommentMapper;
import com.qingwenwei.persistence.dao.PostMapper;
import com.qingwenwei.persistence.dao.UserMapper;
import com.qingwenwei.persistence.dao.VerificationTokenMapper;
import com.qingwenwei.persistence.model.Comment;
import com.qingwenwei.persistence.model.Post;
import com.qingwenwei.persistence.model.User;
import com.qingwenwei.persistence.model.VerificationToken;
import com.qingwenwei.service.StorageService;
import com.qingwenwei.service.UserService;
import com.qingwenwei.web.dto.UserRegistrationDto;
import com.qingwenwei.web.dto.UserSettingsDto;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private VerificationTokenMapper verificationTokenMapper;

	@Autowired
	private StorageService storageService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ApplicationEventPublisher evenPublisher;

	@Override
	public User findById(Long id) {
		return userMapper.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userMapper.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public int save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userMapper.save(user);
	}

	@Override
	public Map<String, Object> getUserProfileAndPostsByUserIdByTabType(Long userId, String tabType) {
		if (null == userId) {
			return null;
		}
		User user = this.userMapper.findById(userId);
		if (null == user) {
			return null;
		}
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("user", user);
		String activeTab = tabType == null ? "posts" : tabType;
		if ("posts".equalsIgnoreCase(activeTab)) {
			List<Post> posts = this.postMapper.findPostsByUserId(userId);
			attributes.put("posts", posts);
		} else if ("comments".equalsIgnoreCase(activeTab)) {
			List<Comment> comments = this.commentMapper.findCommentsByUserId(userId);
			attributes.put("comments", comments);
		}
		attributes.put("activeTab", activeTab);
		return attributes;
	}

	@Override
	public User findAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return this.userMapper.findByUsername(username);
	}

	@Override
	public Map<String, Object> updateUserProfile(UserSettingsDto userSettingsDto) {
		Map<String, Object> attributes = new HashMap<>();
		String authenticatedUsername = this.findAuthenticatedUser().getUsername();
		if (null == authenticatedUsername || authenticatedUsername.equalsIgnoreCase("") || null == userSettingsDto
				|| userSettingsDto.getEmail().isEmpty() || userSettingsDto.getEmail().equals("")) {
			attributes.put("uploadResultMessage", "uploadFailure");
			return attributes;
		}

		// update user profile
		User user = this.storageService.store(userSettingsDto.getAvatar(), authenticatedUsername);
		if (null == user) {
			attributes.put("uploadResultMessage", "uploadFailure");
			user = this.findAuthenticatedUser(); // find authenticated user if no user found
		}
		user.setEmail(userSettingsDto.getEmail());
		user.setBio(userSettingsDto.getBio());
		this.userMapper.update(user);

		// return attributes
		attributes.put("user", user);
		attributes.put("uploadResultMessage", "uploadSuccess");
		return attributes;
	}

	@Override
	public Map<String, Object> getUserSettingPage() {
		User user = this.findAuthenticatedUser();
		UserSettingsDto newUserSettingsForm = new UserSettingsDto();
		newUserSettingsForm.setBio(user.getBio());
		newUserSettingsForm.setEmail(user.getEmail());
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("user", user);
		attributes.put("userSettingsDto", newUserSettingsForm);
		return attributes;
	}

	@Override
	public Map<String, Object> registerUserAccount(UserRegistrationDto userDto, HttpServletRequest request) {
		Map<String, Object> attributes = new HashMap<>();

		// save newly registered user
		User user = new User();
		user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setDateCreated(new Timestamp(System.currentTimeMillis()));
		user.activated(false);
		user.setRoles(User.USER);

		// save new user and get number of affected row
		int affectedRow = userMapper.save(user);

		// publish registration event
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort();
		Locale locale = request.getLocale();
		OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(user.getUsername(), appUrl, locale);
		this.evenPublisher.publishEvent(event);

		// populate attributes
		String registrationResult = affectedRow == 1 ? "success" : "failure";
		attributes.put("userRegistrationResult", registrationResult);
		return attributes;
	}

	@Override
	public Map<String, Object> confirmUserRegistrationWithToken(String token) {
		Map<String, Object> attributes = new HashMap<>();
		VerificationToken verificationToken = this.verificationTokenMapper.findByToken(token);
		if (null == verificationToken) {
			return null; // 404 exception
		}
		// check if expire time is still within 24 hours
		boolean tokenValid = verificationToken.getExpiryDate().getTime() - System.currentTimeMillis() > 0;
		if (tokenValid) {
			String username = verificationToken.getUser().getUsername();
			User user = this.userMapper.findByUsername(username);
			user.activated(true);
			this.userMapper.update(user);
			attributes.put("registrationActivationResult", "success");
		} else {
			attributes.put("registrationActivationResult", "failure");
		}
		return attributes;
	}

}

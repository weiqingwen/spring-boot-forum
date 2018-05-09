package com.qingwenwei.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.qingwenwei.exception.StorageException;
import com.qingwenwei.persistence.dao.UserMapper;
import com.qingwenwei.persistence.model.User;
import com.qingwenwei.service.StorageService;

@Service("storageService")
public class StorageServiceImpl implements StorageService {

	private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

	@Value("${resource.staticResourceLocation}")
	private String staticResourceLocation;

	@Autowired
	private UserMapper userMapper;

	@Override
	public User store(MultipartFile file, String username) { // re-factor needed
		if (null == file || file.isEmpty() || null == username || username.isEmpty() || username.equalsIgnoreCase("")) {
			return null;
		}

		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			Path dir = Paths.get(this.staticResourceLocation + "/" + username);
			if (Files.notExists(dir)) {
				Files.createDirectory(dir);
			}
			Path avatarLocation = Paths.get(this.staticResourceLocation + "/" + username).resolve(filename);
			Files.copy(file.getInputStream(), avatarLocation, StandardCopyOption.REPLACE_EXISTING);
			logger.info("Saved file to >> " + avatarLocation);

			// update user avatar location
			User user = this.userMapper.findByUsername(username);
			user.setAvatarLocation("avatar/" + username + "/" + filename);
			return user;
		} catch (Exception e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(Paths.get(this.staticResourceLocation));
		} catch (Exception e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public void deleteAll() {

	}
}

package com.qingwenwei.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserSettingsDto {

	private MultipartFile avatar;
	private String bio;
	private String email;
	private String password;
	private String passwordConfirmation;

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "NewUserSettingsForm [avatar=" + avatar + ", bio=" + bio + ", email=" + email + ", password=" + password
				+ ", passwordConfirmation=" + passwordConfirmation + "]";
	}

}

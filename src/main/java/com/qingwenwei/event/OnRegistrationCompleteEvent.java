package com.qingwenwei.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final String username;
	private final String appUrl;
	private final Locale locale;

	public OnRegistrationCompleteEvent(String username, String appUrl, Locale locale) {
		super(username);
		this.username = username;
		this.appUrl = appUrl;
		this.locale = locale;
	}

	public String getUsername() {
		return username;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

}

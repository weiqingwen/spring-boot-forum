package com.qingwenwei.util.avatarGenerator;

import java.awt.Color;

public class AvatarSpec {

	public String text;
	public int height;
	public int width;
	public Color textColor = Color.WHITE;
	public Color backgroundColor = Color.BLACK;

	public AvatarSpec(String text, int height, int width) {
		this.text = text;
		this.height = height;
		this.width = width;
	}

}

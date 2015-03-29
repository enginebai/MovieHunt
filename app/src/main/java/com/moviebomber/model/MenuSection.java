package com.moviebomber.model;

/**
 * This class represents the menu section in navigation drawer.
 * Created by engine on 15/3/29.
 */
public class MenuSection {

	public static final int NO_ICON = -1;

	private int iconRes;
	private String title;
	private int notificationCount;

	public MenuSection(int iconRes, String title) {
		this.iconRes = iconRes;
		this.title = title;
	}

	public int getIconRes() {
		return iconRes;
	}

	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(int notificationCount) {
		this.notificationCount = notificationCount;
	}
}

package com.moviebomber.model.utils;

/**
 * Represents the order of movie list tab and
 * the parameter used to query the movie list.
 * Created by engine on 15/3/29.
 */
public enum MovieListTab {
	THIS_WEEK("thisweek"),
	IN_THEATER("intheaters"),
	COMING_SOON("comingsoon");
	MovieListTab(String name) {
		this.mName = name;
	}

	private String mName;

	@Override
	public String toString() {
		return this.mName;
	}
}

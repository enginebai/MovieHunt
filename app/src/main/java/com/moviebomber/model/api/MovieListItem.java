package com.moviebomber.model.api;

/**
 * This class is used to store the response of movie list query API.
 * Created by engine on 15/3/29.
 */
public class MovieListItem {
	// TODO: add member of api response
	private String title;
	private String cover;

	public MovieListItem(String title, String cover) {
		this.title = title;
		this.cover = cover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
}

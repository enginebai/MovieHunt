package com.moviebomber.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is used to store the response of movie list query API.
 * Created by engine on 15/3/29.
 */
public class MovieListItem {

	@Expose
	private String duration;
	@Expose
	private Integer id;
	@SerializedName("release_date")
	@Expose
	private String releaseDate;
	@SerializedName("thumbnail_url")
	@Expose
	private String thumbnailUrl;
	@SerializedName("title_chinese")
	@Expose
	private String titleChinese;

	/**
	 *
	 * @return
	 * The duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 *
	 * @param duration
	 * The duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 *
	 * @return
	 * The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 * The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *
	 * @return
	 * The releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 *
	 * @param releaseDate
	 * The release_date
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 *
	 * @return
	 * The thumbnailUrl
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	/**
	 *
	 * @param thumbnailUrl
	 * The thumbnail_url
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 *
	 * @return
	 * The titleChinese
	 */
	public String getTitleChinese() {
		return titleChinese;
	}

	/**
	 *
	 * @param titleChinese
	 * The title_chinese
	 */
	public void setTitleChinese(String titleChinese) {
		this.titleChinese = titleChinese;
	}

}

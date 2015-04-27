package com.moviebomber.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by engine on 15/4/28.
 */
public class Article {

	public enum BomberStatus {
		GOOD("good"), NORMAL("normal"), BAD("bad");

		private String status;

		BomberStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return this.status;
		}
	}

	@SerializedName("article_author")
	@Expose
	private String articleAuthor;
	@SerializedName("article_date")
	@Expose
	private String articleDate;
	@SerializedName("article_id")
	@Expose
	private String articleId;
	@SerializedName("bomber_status")
	@Expose
	private String bomberStatus;
	@SerializedName("hush_count")
	@Expose
	private Object hushCount;
	@Expose
	private Integer id;
	@SerializedName("movie_id")
	@Expose
	private Integer movieId;
	@SerializedName("push_count")
	@Expose
	private Object pushCount;
	@Expose
	private String title;
	@SerializedName("update_time")
	@Expose
	private String updateTime;
	@Expose
	private String url;

	/**
	 *
	 * @return
	 * The articleAuthor
	 */
	public String getArticleAuthor() {
		return articleAuthor;
	}

	/**
	 *
	 * @param articleAuthor
	 * The article_author
	 */
	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	/**
	 *
	 * @return
	 * The articleDate
	 */
	public String getArticleDate() {
		return articleDate;
	}

	/**
	 *
	 * @param articleDate
	 * The article_date
	 */
	public void setArticleDate(String articleDate) {
		this.articleDate = articleDate;
	}

	/**
	 *
	 * @return
	 * The articleId
	 */
	public String getArticleId() {
		return articleId;
	}

	/**
	 *
	 * @param articleId
	 * The article_id
	 */
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/**
	 *
	 * @return
	 * The bomberStatus
	 */
	public String getBomberStatus() {
		return bomberStatus;
	}

	/**
	 *
	 * @param bomberStatus
	 * The bomber_status
	 */
	public void setBomberStatus(String bomberStatus) {
		this.bomberStatus = bomberStatus;
	}

	/**
	 *
	 * @return
	 * The hushCount
	 */
	public Object getHushCount() {
		return hushCount;
	}

	/**
	 *
	 * @param hushCount
	 * The hush_count
	 */
	public void setHushCount(Object hushCount) {
		this.hushCount = hushCount;
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
	 * The movieId
	 */
	public Integer getMovieId() {
		return movieId;
	}

	/**
	 *
	 * @param movieId
	 * The movie_id
	 */
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	/**
	 *
	 * @return
	 * The pushCount
	 */
	public Object getPushCount() {
		return pushCount;
	}

	/**
	 *
	 * @param pushCount
	 * The push_count
	 */
	public void setPushCount(Object pushCount) {
		this.pushCount = pushCount;
	}

	/**
	 *
	 * @return
	 * The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *
	 * @param title
	 * The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 *
	 * @return
	 * The updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 *
	 * @param updateTime
	 * The update_time
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 *
	 * @return
	 * The url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 *
	 * @param url
	 * The url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}

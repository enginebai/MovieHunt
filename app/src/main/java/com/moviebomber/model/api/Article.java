package com.moviebomber.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by engine on 15/4/28.
 */
public class Article implements Serializable, Parcelable{

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
	private Integer hushCount;
	@Expose
	private Integer id;
	@SerializedName("movie_id")
	@Expose
	private Integer movieId;
	@SerializedName("push_count")
	@Expose
	private Integer pushCount;
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
	public void setHushCount(Integer hushCount) {
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
	public Integer getPushCount() {
		return pushCount;
	}

	/**
	 *
	 * @param pushCount
	 * The push_count
	 */
	public void setPushCount(Integer pushCount) {
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.articleAuthor);
		dest.writeString(this.articleDate);
		dest.writeString(this.articleId);
		dest.writeString(this.bomberStatus);
		dest.writeValue(this.hushCount);
		dest.writeValue(this.id);
		dest.writeValue(this.movieId);
		dest.writeValue(this.pushCount);
		dest.writeString(this.title);
		dest.writeString(this.updateTime);
		dest.writeString(this.url);
	}

	public Article() {
	}

	private Article(Parcel in) {
		this.articleAuthor = in.readString();
		this.articleDate = in.readString();
		this.articleId = in.readString();
		this.bomberStatus = in.readString();
		this.hushCount = (Integer)in.readValue(Integer.class.getClassLoader());
		this.id = (Integer) in.readValue(Integer.class.getClassLoader());
		this.movieId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.pushCount = (Integer)in.readValue(Integer.class.getClassLoader());
		this.title = in.readString();
		this.updateTime = in.readString();
		this.url = in.readString();
	}

	public static final Creator<Article> CREATOR = new Creator<Article>() {
		public Article createFromParcel(Parcel source) {
			return new Article(source);
		}

		public Article[] newArray(int size) {
			return new Article[size];
		}
	};
}

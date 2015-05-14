
package com.moviebomber.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieInfo implements Parcelable {

	public static final String TABLE_NAME = "movie_info";

    @SerializedName("actor_list")
    @Expose
    private ArrayList<Actor> actorList = new ArrayList<Actor>();
    @SerializedName("content_rating")
    @Expose
    private String contentRating;
    @Expose
    private String description;
    @Expose
    private String director;
    @Expose
    private String duration;
    @Expose
    private String film;
    @SerializedName("genre_list")
    @Expose
    private ArrayList<Genre> genreList = new ArrayList<Genre>();
    @Expose
    private Integer id;
    @SerializedName("imdb_url")
    @Expose
    private String imdbUrl;
    @SerializedName("official_website")
    @Expose
    private String officialWebsite;
    @SerializedName("photo_list")
    @Expose
    private ArrayList<Photo> photoList = new ArrayList<Photo>();
    @SerializedName("photo_list_url")
    @Expose
    private String photoListUrl;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("release_status")
    @Expose
    private String releaseStatus;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
	@SerializedName("thumbnail_path")
	@Expose
	private String thumbnailPath;
    @SerializedName("title_chinese")
    @Expose
    private String titleChinese;
    @SerializedName("title_english")
    @Expose
    private String titleEnglish;
    @SerializedName("trailer_list")
    @Expose
    private ArrayList<Trailer> trailerList = new ArrayList<Trailer>();
    @SerializedName("trailer_list_url")
    @Expose
    private String trailerListUrl;
    @SerializedName("yahoo_detail_id")
    @Expose
    private String yahooDetailId;
    @SerializedName("yahoo_detail_url")
    @Expose
    private String yahooDetailUrl;
	@SerializedName("article_list")
	@Expose
	private ArrayList<Article> articleList;
    @SerializedName("good_bomber")
    @Expose
    private int goodBomber;
    @SerializedName("normal_bomber")
    @Expose
    private int normalBomber;
    @SerializedName("bad_bomber")
    @Expose
    private int badBomber;

    /**
     * 
     * @return
     *     The actorList
     */
    public ArrayList<Actor> getActorList() {
        return actorList;
    }

    /**
     * 
     * @param actorList
     *     The actor_list
     */
    public void setActorList(ArrayList<Actor> actorList) {
        this.actorList = actorList;
    }

    /**
     * 
     * @return
     *     The contentRating
     */
    public String getContentRating() {
        return contentRating;
    }

    /**
     * 
     * @param contentRating
     *     The content_rating
     */
    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The director
     */
    public String getDirector() {
        return director;
    }

    /**
     * 
     * @param director
     *     The director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The film
     */
    public String getFilm() {
        return film;
    }

    /**
     * 
     * @param film
     *     The film
     */
    public void setFilm(String film) {
        this.film = film;
    }

    /**
     * 
     * @return
     *     The genreList
     */
    public ArrayList<Genre> getGenreList() {
        return genreList;
    }

    /**
     * 
     * @param genreList
     *     The genre_list
     */
    public void setGenreList(ArrayList<Genre> genreList) {
        this.genreList = genreList;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The imdbUrl
     */
    public String getImdbUrl() {
        return imdbUrl;
    }

    /**
     * 
     * @param imdbUrl
     *     The imdb_url
     */
    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    /**
     * 
     * @return
     *     The officialWebsite
     */
    public String getOfficialWebsite() {
        return officialWebsite;
    }

    /**
     * 
     * @param officialWebsite
     *     The official_website
     */
    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    /**
     * 
     * @return
     *     The photoList
     */
    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }

    /**
     * 
     * @param photoList
     *     The photo_list
     */
    public void setPhotoList(ArrayList<Photo> photoList) {
        this.photoList = photoList;
    }

    /**
     * 
     * @return
     *     The photoListUrl
     */
    public String getPhotoListUrl() {
        return photoListUrl;
    }

    /**
     * 
     * @param photoListUrl
     *     The photo_list_url
     */
    public void setPhotoListUrl(String photoListUrl) {
        this.photoListUrl = photoListUrl;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 
     * @return
     *     The releaseStatus
     */
    public String getReleaseStatus() {
        return releaseStatus;
    }

    /**
     * 
     * @param releaseStatus
     *     The release_status
     */
    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    /**
     * 
     * @return
     *     The thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * 
     * @param thumbnailUrl
     *     The thumbnail_url
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * 
     * @return
     *     The titleChinese
     */
    public String getTitleChinese() {
        return titleChinese;
    }

    /**
     * 
     * @param titleChinese
     *     The title_chinese
     */
    public void setTitleChinese(String titleChinese) {
        this.titleChinese = titleChinese;
    }

    /**
     * 
     * @return
     *     The titleEnglish
     */
    public String getTitleEnglish() {
        return titleEnglish;
    }

    /**
     * 
     * @param titleEnglish
     *     The title_english
     */
    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish = titleEnglish;
    }

    /**
     * 
     * @return
     *     The trailerList
     */
    public ArrayList<Trailer> getTrailerList() {
        return trailerList;
    }

    /**
     * 
     * @param trailerList
     *     The trailer_list
     */
    public void setTrailerList(ArrayList<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    /**
     * 
     * @return
     *     The trailerListUrl
     */
    public String getTrailerListUrl() {
        return trailerListUrl;
    }

    /**
     * 
     * @param trailerListUrl
     *     The trailer_list_url
     */
    public void setTrailerListUrl(String trailerListUrl) {
        this.trailerListUrl = trailerListUrl;
    }

    /**
     * 
     * @return
     *     The yahooDetailId
     */
    public String getYahooDetailId() {
        return yahooDetailId;
    }

    /**
     * 
     * @param yahooDetailId
     *     The yahoo_detail_id
     */
    public void setYahooDetailId(String yahooDetailId) {
        this.yahooDetailId = yahooDetailId;
    }

    /**
     * 
     * @return
     *     The yahooDetailUrl
     */
    public String getYahooDetailUrl() {
        return yahooDetailUrl;
    }

    /**
     * 
     * @param yahooDetailUrl
     *     The yahoo_detail_url
     */
    public void setYahooDetailUrl(String yahooDetailUrl) {
        this.yahooDetailUrl = yahooDetailUrl;
    }

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public ArrayList<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(ArrayList<Article> articleList) {
		this.articleList = articleList;
	}

    public int getNormalBomber() {
        return normalBomber;
    }

    public void setNormalBomber(int normalBomber) {
        this.normalBomber = normalBomber;
    }

    public int getGoodBomber() {
        return goodBomber;
    }

    public void setGoodBomber(int goodBomber) {
        this.goodBomber = goodBomber;
    }

    public int getBadBomber() {
        return badBomber;
    }

    public void setBadBomber(int badBomber) {
        this.badBomber = badBomber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.actorList);
        dest.writeString(this.contentRating);
        dest.writeString(this.description);
        dest.writeString(this.director);
        dest.writeString(this.duration);
        dest.writeString(this.film);
        dest.writeSerializable(this.genreList);
        dest.writeValue(this.id);
        dest.writeString(this.imdbUrl);
        dest.writeString(this.officialWebsite);
        dest.writeSerializable(this.photoList);
        dest.writeString(this.photoListUrl);
        dest.writeString(this.releaseDate);
        dest.writeString(this.releaseStatus);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.thumbnailPath);
        dest.writeString(this.titleChinese);
        dest.writeString(this.titleEnglish);
        dest.writeSerializable(this.trailerList);
        dest.writeString(this.trailerListUrl);
        dest.writeString(this.yahooDetailId);
        dest.writeString(this.yahooDetailUrl);
        dest.writeSerializable(this.articleList);
        dest.writeInt(this.goodBomber);
        dest.writeInt(this.normalBomber);
        dest.writeInt(this.badBomber);
    }

    public MovieInfo() {
    }

    private MovieInfo(Parcel in) {
        this.actorList = (ArrayList<Actor>) in.readSerializable();
        this.contentRating = in.readString();
        this.description = in.readString();
        this.director = in.readString();
        this.duration = in.readString();
        this.film = in.readString();
        this.genreList = (ArrayList<Genre>) in.readSerializable();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imdbUrl = in.readString();
        this.officialWebsite = in.readString();
        this.photoList = (ArrayList<Photo>) in.readSerializable();
        this.photoListUrl = in.readString();
        this.releaseDate = in.readString();
        this.releaseStatus = in.readString();
        this.thumbnailUrl = in.readString();
        this.thumbnailPath = in.readString();
        this.titleChinese = in.readString();
        this.titleEnglish = in.readString();
        this.trailerList = (ArrayList<Trailer>) in.readSerializable();
        this.trailerListUrl = in.readString();
        this.yahooDetailId = in.readString();
        this.yahooDetailUrl = in.readString();
        this.articleList = (ArrayList<Article>) in.readSerializable();
        this.goodBomber = in.readInt();
        this.normalBomber = in.readInt();
        this.badBomber = in.readInt();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        public MovieInfo createFromParcel(Parcel source) {
            return new MovieInfo(source);
        }

        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}

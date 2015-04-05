
package com.moviebomber.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieInfo implements Serializable{

	public static final String TABLE_NAME = "movie_info";

    @SerializedName("actor_list")
    @Expose
    private ArrayList<ActorList> actorList = new ArrayList<ActorList>();
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
    private ArrayList<GenreList> genreList = new ArrayList<GenreList>();
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
    private ArrayList<PhotoList> photoList = new ArrayList<PhotoList>();
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
    @SerializedName("title_chinese")
    @Expose
    private String titleChinese;
    @SerializedName("title_english")
    @Expose
    private String titleEnglish;
    @SerializedName("trailer_list")
    @Expose
    private ArrayList<TrailerList> trailerList = new ArrayList<TrailerList>();
    @SerializedName("trailer_list_url")
    @Expose
    private String trailerListUrl;
    @SerializedName("yahoo_detail_id")
    @Expose
    private String yahooDetailId;
    @SerializedName("yahoo_detail_url")
    @Expose
    private String yahooDetailUrl;

    /**
     * 
     * @return
     *     The actorList
     */
    public ArrayList<ActorList> getActorList() {
        return actorList;
    }

    /**
     * 
     * @param actorList
     *     The actor_list
     */
    public void setActorList(ArrayList<ActorList> actorList) {
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
    public ArrayList<GenreList> getGenreList() {
        return genreList;
    }

    /**
     * 
     * @param genreList
     *     The genre_list
     */
    public void setGenreList(ArrayList<GenreList> genreList) {
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
    public ArrayList<PhotoList> getPhotoList() {
        return photoList;
    }

    /**
     * 
     * @param photoList
     *     The photo_list
     */
    public void setPhotoList(ArrayList<PhotoList> photoList) {
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
    public ArrayList<TrailerList> getTrailerList() {
        return trailerList;
    }

    /**
     * 
     * @param trailerList
     *     The trailer_list
     */
    public void setTrailerList(ArrayList<TrailerList> trailerList) {
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

}

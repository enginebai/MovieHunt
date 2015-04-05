
package com.moviebomber.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerList {

    @Expose
    private Integer id;
    @SerializedName("movie_id")
    @Expose
    private Integer movieId;
    @Expose
    private String title;
    @Expose
    private String url;

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
     *     The movieId
     */
    public Integer getMovieId() {
        return movieId;
    }

    /**
     * 
     * @param movieId
     *     The movie_id
     */
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}


package com.moviebomber.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreList {

    @Expose
    private String genre;
    @Expose
    private Integer id;
    @SerializedName("movie_id")
    @Expose
    private Integer movieId;

    /**
     * 
     * @return
     *     The genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * 
     * @param genre
     *     The genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
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

}

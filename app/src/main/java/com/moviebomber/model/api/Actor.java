
package com.moviebomber.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Actor implements Serializable{

    @SerializedName("actor_name")
    @Expose
    private String actorName;
    @Expose
    private Integer id;
    @SerializedName("movie_id")
    @Expose
    private Integer movieId;

    /**
     * 
     * @return
     *     The actorName
     */
    public String getActorName() {
        return actorName;
    }

    /**
     * 
     * @param actorName
     *     The actor_name
     */
    public void setActorName(String actorName) {
        this.actorName = actorName;
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


package com.moviebomber.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trailer implements Serializable, Parcelable{

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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.id);
		dest.writeValue(this.movieId);
		dest.writeString(this.title);
		dest.writeString(this.url);
	}

	public Trailer() {
	}

	private Trailer(Parcel in) {
		this.id = (Integer) in.readValue(Integer.class.getClassLoader());
		this.movieId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.title = in.readString();
		this.url = in.readString();
	}

	public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
		public Trailer createFromParcel(Parcel source) {
			return new Trailer(source);
		}

		public Trailer[] newArray(int size) {
			return new Trailer[size];
		}
	};
}

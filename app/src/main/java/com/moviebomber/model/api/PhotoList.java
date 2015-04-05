
package com.moviebomber.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhotoList implements Serializable, Parcelable{

    @Expose
    private Integer id;
    @SerializedName("movie_id")
    @Expose
    private Integer movieId;
    @Expose
    private String name;
    @Expose
    private String path;
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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The path
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     * @param path
     *     The path
     */
    public void setPath(String path) {
        this.path = path;
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
		dest.writeString(this.name);
		dest.writeString(this.path);
		dest.writeString(this.url);
	}

	public PhotoList() {
	}

	private PhotoList(Parcel in) {
		this.id = (Integer) in.readValue(Integer.class.getClassLoader());
		this.movieId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.name = in.readString();
		this.path = in.readString();
		this.url = in.readString();
	}

	public static final Creator<PhotoList> CREATOR = new Creator<PhotoList>() {
		public PhotoList createFromParcel(Parcel source) {
			return new PhotoList(source);
		}

		public PhotoList[] newArray(int size) {
			return new PhotoList[size];
		}
	};
}

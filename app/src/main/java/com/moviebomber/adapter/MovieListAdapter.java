package com.moviebomber.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebomber.R;
import com.moviebomber.model.api.MovieListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by engine on 15/3/29.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListItemHolder> {

	private List<MovieListItem> mMovieList = new ArrayList<>();

	public MovieListAdapter(List<MovieListItem> mMovieList) {
		this.mMovieList = mMovieList;
	}

	public List<MovieListItem> getMovieList() {
		return mMovieList;
	}

	public void setMovieList(List<MovieListItem> mMovieList) {
		this.mMovieList = mMovieList;
	}

	@Override
	public MovieListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MovieListItemHolder(LayoutInflater.from(parent.getContext())
				.inflate(R.layout.card_movie_list, parent, false));
	}

	@Override
	public void onBindViewHolder(MovieListItemHolder holder, int position) {
		MovieListItem movieItem = this.mMovieList.get(position);
		holder.mTextMovieName.setText(movieItem.getTitle());
		Picasso.with(holder.mImageMovieCover.getContext())
				.load(movieItem.getCover())
				.into(holder.mImageMovieCover);
	}

	@Override
	public int getItemCount() {
		// FIXME:
		return 10;
//		return this.mMovieList.size();
	}

	class MovieListItemHolder extends RecyclerView.ViewHolder {
		@InjectView(R.id.image_movie_cover)
		ImageView mImageMovieCover;

		@InjectView(R.id.text_movie_name)
		TextView mTextMovieName;

		MovieListItemHolder(View itemView) {
			super(itemView);
			ButterKnife.inject(this, itemView);
		}
	}
}

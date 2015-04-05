package com.moviebomber.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebomber.R;
import com.moviebomber.model.api.MovieListItem;
import com.moviebomber.ui.activity.MovieDetailActivity;
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
	private Context mContext;

	public MovieListAdapter(Context context, List<MovieListItem> mMovieList) {
		this.mContext = context;
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
		final MovieListItem movieItem = this.mMovieList.get(position);
		holder.mTextMovieName.setText(movieItem.getTitleChinese());
		String thumbnailUrl = movieItem.getThumbnailUrl();
		thumbnailUrl = thumbnailUrl.replace("mpost4", "mpost");
		Picasso.with(holder.mImageMovieCover.getContext())
				.load(thumbnailUrl)
				.into(holder.mImageMovieCover);
		holder.mCardMovie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent movieDetail = new Intent(mContext, MovieDetailActivity.class);
				movieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieItem.getId());
				mContext.startActivity(movieDetail);
			}
		});
	}

	@Override
	public int getItemCount() {
		return this.mMovieList.size();
	}

	class MovieListItemHolder extends RecyclerView.ViewHolder {
		@InjectView(R.id.card_movie_list_item)
		CardView mCardMovie;

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

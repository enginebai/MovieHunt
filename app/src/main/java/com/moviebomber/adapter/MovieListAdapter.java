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

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.moviebomber.R;
import com.moviebomber.model.api.MovieListItem;
import com.moviebomber.ui.activity.MainActivity;
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
		String thumbnailPath = movieItem.getThumbnailPath();

//		thumbnailUrl = thumbnailUrl.replace("mpost4", "mpost");
		Picasso.with(holder.mImagePoster.getContext())
				.load(MainActivity.getResizePhoto(this.mContext, thumbnailPath))
				.into(holder.mImagePoster);
//		LabelView label = new LabelView(this.mContext);
//		label.setText("POP");
//		label.setTextColor(this.mContext.getResources().getColor(android.R.color.white));
//		label.setBackgroundColor(this.mContext.getResources().getColor(R.color.accent_light));
//		label.setTargetView(holder.mImageMovieCover, 10, LabelView.Gravity.LEFT_TOP);
		if (movieItem.getPhotoLists().size() > 0) {
			String coverUrl = movieItem.getPhotoLists().get(
					(int) (Math.random() * movieItem.getPhotoLists().size())).getPath();
//			coverUrl = coverUrl.replace("mpho3", "mpho");
			Picasso.with(holder.mImageMovieCover.getContext())
					.load(MainActivity.getResizePhoto(this.mContext, coverUrl)).into(holder.mImageMovieCover);
		}
		holder.mTextDuration.setText(this.mContext.getResources().getString(R.string.text_duration)
				+ ": " + movieItem.getDuration());
		holder.mTextReleaseDate.setText(this.mContext.getResources().getString(R.string.text_release_date)
				+ ": " + movieItem.getReleaseDate());

		holder.mCardMovie.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent movieDetail = new Intent(mContext, MovieDetailActivity.class);
				movieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieItem.getId());
				mContext.startActivity(movieDetail);
			}
		});

		holder.mTextGoodBomber.setText(String.valueOf(movieItem.getGoodBomber()));
		holder.mTextNormalBomber.setText(String.valueOf(movieItem.getNormalBomber()));
		holder.mTextBadBomber.setText(String.valueOf(movieItem.getBadBomber()));
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

		@InjectView(R.id.image_movie_poster)
		ImageView mImagePoster;

		@InjectView(R.id.text_movie_name)
		TextView mTextMovieName;

		@InjectView(R.id.text_release_date)
		TextView mTextReleaseDate;
		@InjectView(R.id.text_duration)
		TextView mTextDuration;
		@InjectView(R.id.text_good_bomber)
		TextView mTextGoodBomber;
		@InjectView(R.id.progress_good_bomber)
		ArcProgress mProgressGoodBomber;
		@InjectView(R.id.text_normal_bomber)
		TextView mTextNormalBomber;
		@InjectView(R.id.progress_normal_bomber)
		ArcProgress mProgressNormalBomber;
		@InjectView(R.id.text_bad_bomber)
		TextView mTextBadBomber;
		@InjectView(R.id.progress_bad_bomber)
		ArcProgress mProgressBadBomber;

		MovieListItemHolder(View itemView) {
			super(itemView);
			ButterKnife.inject(this, itemView);
		}
	}
}

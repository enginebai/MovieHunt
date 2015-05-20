package com.moviebomber.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.moviebomber.R;
import com.moviebomber.model.api.MovieInfo;
import com.moviebomber.model.api.Trailer;
import com.nineoldandroids.view.ViewHelper;
import com.orhanobut.logger.Logger;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrailerActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,
		ObservableScrollViewCallbacks {

	public static final String EXTRA_TRAILER_LIST = "TRAILER";
	private static final String KEY = "AIzaSyAbR9V_oVGtZ4AEd3Er2ntbwh5zStfAW_s";

	@InjectView(R.id.list_trailer)
	ObservableListView mListTrailer;
	@InjectView(R.id.fab_play)
	FloatingActionButton mFabPlay;
	@InjectView(R.id.image_movie_cover)
	ImageView mImageCover;
	@InjectView(R.id.overlay)
	View mViewOverlay;
	@InjectView(R.id.text_trailer_title)
	TextView mTextTitle;

	private int mImageCoverHeight;
	private String mImageCoverUrl;
	private MovieInfo mMovieInfo;
	private TrailerAdapter mAdapter;

	private Map<View, YouTubeThumbnailLoader> mListThumbnailLoaderMap = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailer);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window w = getWindow(); // in Activity's onCreate() for instance
//			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		ButterKnife.inject(this);
		if (this.getIntent() != null) {
			this.mImageCoverUrl = this.getIntent().getStringExtra(MovieDetailActivity.EXTRA_MOVIE_COVER);
			this.mMovieInfo = this.getIntent().getParcelableExtra(MovieDetailActivity.EXTRA_MOVIE_DETAIL);
			List<Trailer> mTrailerList = this.getIntent().getParcelableArrayListExtra(EXTRA_TRAILER_LIST);
			final List<Trailer> newTrailerList = new ArrayList<>();
			// filter playerlist or user
			for (Trailer t : mTrailerList) {
				if (t.getUrl().contains("v="))
					newTrailerList.add(t);
			}
			if (newTrailerList.size() > 0) {
				this.mAdapter = new TrailerAdapter(this, R.layout.item_trailer, newTrailerList.subList(1, newTrailerList.size()));
				this.mListTrailer.setAdapter(mAdapter);
				this.mListTrailer.setOnItemClickListener(this);
//				this.setupHeader();
				mFabPlay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(TrailerActivity.this,
								getVideoId(newTrailerList.get(0).getUrl()), true, false);
						startActivity(intent);
					}
				});
				this.mImageCoverHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
				this.mListTrailer.setScrollViewCallbacks(this);

				// Set padding view for ListView. This is the flexible space.
				View paddingView = new View(this);
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
						mImageCoverHeight);
				paddingView.setLayoutParams(lp);
				paddingView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (mAdapter.getCount() >= 1)
							playTrailer(0);
					}
				});
				// This is required to disable header's list selector effect
				paddingView.setClickable(true);
				mListTrailer.addHeaderView(paddingView);
				if (this.mImageCoverUrl != null) {
					Picasso.with(mImageCover.getContext())
							.load(this.mImageCoverUrl)
							.into(mImageCover);
				}
				if (this.mMovieInfo != null)
					mTextTitle.setText(this.mMovieInfo.getTitleChinese());
				View footerView = LayoutInflater.from(this).inflate(R.layout.footer_trailer_button, null);
				Button buttonSearchMore = (Button) footerView.findViewById(R.id.button_trailer_more);
				buttonSearchMore.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = YouTubeIntents.createSearchIntent(TrailerActivity.this, mMovieInfo.getTitleChinese());
						startActivity(intent);
					}
				});
				this.mListTrailer.addFooterView(footerView);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		playTrailer(position - 1);
	}

	private void playTrailer(int position) {
		try {
			Trailer trailer = this.mAdapter.getItem(position);
			Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(this,
					getVideoId(trailer.getUrl()), true, true);
			startActivity(intent);
		} catch (RuntimeException e) {
			Logger.e(String.format("%d, %s\n",
					this.mMovieInfo.getId(),
					this.mMovieInfo.getTitleChinese()
					));
			throw e;
		}
	}

	public static String getVideoId(String url) {
		Logger.wtf(url);
		return url.split("=")[1];
	}

	private void setupHeader() {
		View headerView = LayoutInflater.from(this).inflate(R.layout.header_trailer, null);
		ImageView imageCover = (ImageView)headerView.findViewById(R.id.image_movie_cover);
		if (this.mImageCoverUrl != null) {
			Picasso.with(imageCover.getContext())
					.load(this.mImageCoverUrl)
					.into(imageCover);
		}
//		YouTubeThumbnailView imageTrailer = (YouTubeThumbnailView)headerView.findViewById(R.id.image_trailer);
//		imageTrailer.initialize(KEY, new YouTubeThumbnailView.OnInitializedListener() {
//			@Override
//			public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
//				youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
//					@Override
//					public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
//
//					}
//
//					@Override
//					public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
//
//					}
//				});
//				youTubeThumbnailLoader.setVideo(trailer.getUrl().split("=")[1]);
//			}
//
//			@Override
//			public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
//
//			}
//		});
		TextView textTitle = (TextView)headerView.findViewById(R.id.text_trailer_title);
		if (this.mMovieInfo != null)
			textTitle.setText(this.mMovieInfo.getTitleChinese());
		this.mListTrailer.addHeaderView(headerView);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
		int actionbarSize = getResources().getDimensionPixelSize(R.dimen.actionbar_height);
		float flexibleRange = mImageCoverHeight - actionbarSize;
		int minOverlayTranslationY = actionbarSize - mViewOverlay.getHeight();

		// translate over and image
		ViewHelper.setTranslationY(mViewOverlay, ScrollUtils.getFloat(-scrollY, minOverlayTranslationY, 0));
		ViewHelper.setTranslationY(mImageCover, ScrollUtils.getFloat(-scrollY / 2, minOverlayTranslationY, 0));

		// Change alpha of overlay
		ViewHelper.setAlpha(mViewOverlay, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

//		// Scale title text
		float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, 0.3f);
		ViewHelper.setPivotY(mTextTitle, 0);
		ViewHelper.setScaleX(mTextTitle, scale);
		ViewHelper.setScaleY(mTextTitle, scale);

		// Translate title text
		int maxTitleTranslationY = (int) (mImageCoverHeight - mTextTitle.getHeight() * scale);
		int titleTranslationY = maxTitleTranslationY - scrollY;
		ViewHelper.setTranslationY(mTextTitle, titleTranslationY);
		ViewHelper.setTranslationY(mFabPlay, scrollY / 4);

		// change alpha of FAB
		ViewHelper.setAlpha(mFabPlay, ScrollUtils.getFloat(1.0f - (float)scrollY / flexibleRange, 0, 1));
	}

	@Override
	public void onDownMotionEvent() {

	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {

	}

	class TrailerAdapter extends ArrayAdapter<Trailer> implements YouTubeThumbnailView.OnInitializedListener {
		TrailerAdapter(Context context, int resource, List<Trailer> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			String videoUrl = this.getItem(position).getUrl();
//			Logger.wtf(videoUrl);
			String id = videoUrl.split("=")[1];
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
				holder = new ViewHolder(convertView);
				holder.mImageTrailer.setTag(id);
				holder.mImageTrailer.initialize(KEY, this);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				YouTubeThumbnailLoader loader = mListThumbnailLoaderMap.get(holder.mImageTrailer);
				if (loader == null) {
					holder.mImageTrailer.setTag(id);
				} else {
					loader.setVideo(id);
				}
			}
			holder.mTextTitle.setText(this.getItem(position).getTitle().replace("- YouTube", "").trim());
			return convertView;
		}

		@Override
		public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
			String videoId = (String)youTubeThumbnailView.getTag();
			mListThumbnailLoaderMap.put(youTubeThumbnailView, youTubeThumbnailLoader);
			youTubeThumbnailLoader.setVideo(videoId);
		}

		@Override
		public int getCount() {
			return super.getCount() + 1;
		}

		@Override
		public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

		}

		class ViewHolder {
			@InjectView(R.id.image_trailer_thumbnail)
			YouTubeThumbnailView mImageTrailer;
			@InjectView(R.id.text_trailer_title)
			TextView mTextTitle;

			ViewHolder(View itemView) {
				ButterKnife.inject(this, itemView);
			}
		}
	}
}

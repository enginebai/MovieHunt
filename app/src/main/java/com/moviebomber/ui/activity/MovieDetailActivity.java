package com.moviebomber.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.moviebomber.R;
import com.moviebomber.model.api.MovieInfo;
import com.nineoldandroids.view.ViewHelper;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovieDetailActivity extends ActionBarActivity
		implements View.OnClickListener, ObservableScrollViewCallbacks{

	public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	@InjectView(R.id.scroll)
	ObservableScrollView mScrollView;
	@InjectView(R.id.image_photo)
	ImageView mImage;
	@InjectView(R.id.text_title_chinese)
	TextView mTextTitleChinese;
	@InjectView(R.id.text_release_date)
	TextView mTextReleaseDate;
	@InjectView(R.id.text_description)
	TextView mTextDescription;
	@InjectView(R.id.text_duration)
	TextView mTextDuration;
	@InjectView(R.id.text_genres)
	TextView mTextGenres;
	@InjectView(R.id.text_director)
	TextView mTextDirector;
	@InjectView(R.id.text_actors)
	TextView mTextActors;

//	@InjectView(R.id.fab_actions)
//	FloatingActionsMenu fabActionsMenu;
//	@InjectView(R.id.fab_comment)
//	FloatingActionButton fabComment;
//	@InjectView(R.id.fab_photo)
//	FloatingActionButton fabPhoto;
//	@InjectView(R.id.fab_trailer)
//	FloatingActionButton fabTrailer;
//	@InjectView(R.id.fab_share)
//	FloatingActionButton fabShare;
	@InjectView(R.id.button_photo)
	Button mButtonPhoto;
	@InjectView(R.id.button_trailer)
	Button mButtonTrailer;
	@InjectView(R.id.button_comment)
	Button mButtonComment;

	private int mMovieId;
	private MovieInfo mMovieInfo;
	private int mParallaxImageHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window w = getWindow(); // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_movie_detail_v1);
		ButterKnife.inject(this);
		this.setSupportActionBar(this.mToolbar);
		if (this.getSupportActionBar() != null) {
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			this.getSupportActionBar().setTitle("");
		}

		this.mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, this.getResources().getColor(R.color.primary)));
		this.mScrollView.setScrollViewCallbacks(this);
		this.mParallaxImageHeight = this.getResources().getDimensionPixelOffset(R.dimen.parallax_image_height);
//		this.setupButtonIcon();
		if (getIntent() != null) {
			this.mMovieId = this.getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
			this.queryMovieDetail();
//			this.fabComment.setOnClickListener(this);
//			this.fabPhoto.setOnClickListener(this);
//			this.fabShare.setOnClickListener(this);
//			this.fabTrailer.setOnClickListener(this);
		}
	}

	private void queryMovieDetail() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(String.format("%s%s/%s/%d", this.getResources().getString(R.string.api_host),
				this.getResources().getString(R.string.api_root),
				MovieInfo.TABLE_NAME, this.mMovieId), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				Gson gson = new Gson();
				mMovieInfo = gson.fromJson(response.toString(), MovieInfo.class);
				displayMovieDetail(mMovieInfo);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	private void displayMovieDetail(MovieInfo movieInfo) {
		if (movieInfo.getPhotoList().size() > 0) {
			String url = movieInfo.getPhotoList().get(0).getUrl();
			url = url.replace("mpho3", "mpho");
			Picasso.with(this.mImage.getContext())
					.load(url)
					.into(this.mImage);
		}
		this.mTextTitleChinese.setText(movieInfo.getTitleChinese());
		this.mTextReleaseDate.setText(this.getResources().getString(R.string.text_release_date) +
				": " + movieInfo.getReleaseDate());
		this.mTextDescription.setText(movieInfo.getDescription().length() > 30 ?
		movieInfo.getDescription().substring(0, 100) + "..." : movieInfo.getDescription());
		this.mTextDuration.setText(this.getResources().getString(R.string.text_duration) +
				": " + movieInfo.getDuration());
//		if (movieInfo.getGenreList().size() > 0) {
//			for (Genre genre : movieInfo.getGenreList()) {
//				View viewGenre = LayoutInflater.from(this).inflate(R.layout.item_genre, null, false);
//				CardView cardGenreLabel = (CardView) viewGenre.findViewById(R.id.card_genre_label);
//				TextView textGenre = (TextView) cardGenreLabel.findViewById(R.id.text_genre);
//				textGenre.setText(genre.getGenre());
//				this.mViewGenre.addView(cardGenreLabel);
//			}
//		}
		if (movieInfo.getGenreList().size() > 0) {
			StringBuilder genres = new StringBuilder();
			for (int i = 0; i < movieInfo.getGenreList().size(); i++) {
				genres.append(movieInfo.getGenreList().get(i).getGenre());
				if (i < movieInfo.getGenreList().size() - 1)
					genres.append("\t");
			}
			this.mTextGenres.setText(genres.toString());
		}
		this.mTextDirector.setText(movieInfo.getDirector());
		if (movieInfo.getActorList().size() > 0) {
			StringBuilder actors = new StringBuilder();
			for (int i = 0; i < movieInfo.getActorList().size(); i++) {
				actors.append(String.format("%d. %s", i, movieInfo.getActorList().get(i).getActorName()));
				if (i < movieInfo.getActorList().size() - 1)
					actors.append("\n");
			}
			this.mTextActors.setText(actors.toString());
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
//			case R.id.fab_comment:
//				break;
//			case R.id.fab_share:
//				break;
//			case R.id.fab_trailer:
//				break;
//			case R.id.fab_photo:
//				Intent photoList = new Intent(MovieDetailActivity.this, PhotoListActivity.class);
//				photoList.putParcelableArrayListExtra(PhotoListActivity.EXTRA_PHOTO_LIST,
//						mMovieInfo.getPhotoList());
//				startActivity(photoList);
//				break;
		}
//		this.fabActionsMenu.collapse();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
		int primaryColor = this.getResources().getColor(R.color.primary);
		float alpha = 1 - (float)Math.max(0, mParallaxImageHeight - 1.5 * scrollY) / mParallaxImageHeight;
		this.mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, primaryColor));

		// handle image parallex scroll
		ViewHelper.setTranslationY(this.mImage, scrollY / 4);
	}

	@Override
	public void onDownMotionEvent() {

	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {

	}

	//	private void setupButtonIcon() {
//		IconicFontDrawable icon = new IconicFontDrawable(this);
//		icon.setIcon("gmd-dashboard");
//		icon.setIconColor(this.getResources().getColor(android.R.color.holo_blue_light));
//		icon.setBounds(0, 0, 64, 64);
//		mButtonPhoto.setCompoundDrawables(icon, icon, null, null);
//
//	}
}

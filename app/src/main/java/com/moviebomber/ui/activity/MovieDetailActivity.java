package com.moviebomber.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.moviebomber.R;
import com.moviebomber.model.api.MovieInfo;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovieDetailActivity extends ActionBarActivity {

	public static final String EXTRA_MOVIE_ID = "MOVIE_ID";

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
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
	@InjectView(R.id.text_genre)
	TextView mTextGenre;
	@InjectView(R.id.text_director)
	TextView mTextDirector;
	@InjectView(R.id.text_actors)
	TextView mTextActors;

	@InjectView(R.id.fab_comment)
	FloatingActionButton fabComment;
	@InjectView(R.id.fab_photo)
	FloatingActionButton fabPhoto;
	@InjectView(R.id.fab_trailer)
	FloatingActionButton fabTrailer;
	@InjectView(R.id.fab_share)
	FloatingActionButton fabShare;

	private int mMovieId;
	private MovieInfo mMovieInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);
		ButterKnife.inject(this);
		this.setSupportActionBar(this.mToolbar);
		if (this.getSupportActionBar() != null) {
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		if (getIntent() != null) {
			this.mMovieId = this.getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
			this.queryMovieDetail();
			this.setupEvent();
		}
	}

	private void queryMovieDetail() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(String.format("%s%s/%s/%d", this.getResources().getString(R.string.host),
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
		if (movieInfo.getPhotoList().size() > 0)
			Picasso.with(this.mImage.getContext())
					.load(movieInfo.getPhotoList().get(0).getUrl())
					.into(this.mImage);
		this.mTextTitleChinese.setText(movieInfo.getTitleChinese());
		this.mTextReleaseDate.setText(movieInfo.getReleaseDate());
		this.mTextDescription.setText(movieInfo.getDescription());
		this.mTextDuration.setText(movieInfo.getDuration());
		if (movieInfo.getGenreList().size() > 0)
		this.mTextGenre.setText(movieInfo.getGenreList().get(0).getGenre());
		this.mTextDirector.setText(movieInfo.getDirector());
		if (movieInfo.getActorList().size() > 0)
			this.mTextActors.setText(movieInfo.getActorList().get(0).getActorName());
	}

	private void setupEvent() {
		this.fabComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		this.fabPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		this.fabTrailer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		this.fabShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
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
}

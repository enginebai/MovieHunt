package com.moviebomber.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.moviebomber.R;
import com.moviebomber.model.api.Trailer;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrailerActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

	public static final String EXTRA_TRAILER_LIST = "TRAILER";
	private static final String KEY = "AIzaSyAbR9V_oVGtZ4AEd3Er2ntbwh5zStfAW_s";

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	@InjectView(R.id.list_trailer)
	ListView mListTrailer;

	private TrailerAdapter mAdapter;

	private Map<View, YouTubeThumbnailLoader> mListThumbnailLoaderMap = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailer);
		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			Window w = getWindow(); // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		ButterKnife.inject(this);
		this.setSupportActionBar(this.mToolbar);
		if (this.getSupportActionBar() != null) {
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			this.getSupportActionBar().setTitle("");
		}
		if (this.getIntent() != null) {
			List<Trailer> mTrailerList = this.getIntent().getParcelableArrayListExtra(EXTRA_TRAILER_LIST);
			List<Trailer> newTrailerList = new ArrayList<>();
			// filter playerlist or user
			for (Trailer t : mTrailerList) {
				if (t.getUrl().contains("v="))
					newTrailerList.add(t);
			}
			if (newTrailerList.size() > 0) {
				this.mAdapter = new TrailerAdapter(this, R.layout.item_trailer, newTrailerList.subList(1, newTrailerList.size()));
				this.mListTrailer.setAdapter(mAdapter);
				this.mListTrailer.setOnItemClickListener(this);
				this.setupHeader(newTrailerList.get(0));
				View footerView = LayoutInflater.from(this).inflate(R.layout.footer_trailer_button, null);
				Button buttonSearchMore = (Button) footerView.findViewById(R.id.button_trailer_more);
				final String movieName = this.getIntent().getStringExtra(MovieDetailActivity.EXTRA_MOVIE_NAME);
				buttonSearchMore.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = YouTubeIntents.createSearchIntent(TrailerActivity.this, movieName);
						startActivity(intent);
					}
				});
				this.mListTrailer.addFooterView(footerView);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Trailer trailer = this.mAdapter.getItem(position);
		Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(this,
				getVideoId(trailer.getUrl()), true, false);
		startActivity(intent);
	}

	private static String getVideoId(String url) {
		return url.split("=")[1];
	}

	private void setupHeader(final Trailer trailer) {
		View headerView = LayoutInflater.from(this).inflate(R.layout.header_trailer, null);
		YouTubeThumbnailView imageTrailer = (YouTubeThumbnailView)headerView.findViewById(R.id.image_trailer);
		imageTrailer.initialize(KEY, new YouTubeThumbnailView.OnInitializedListener() {
			@Override
			public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
				youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
					@Override
					public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

					}

					@Override
					public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

					}
				});
				youTubeThumbnailLoader.setVideo(trailer.getUrl().split("=")[1]);
			}

			@Override
			public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

			}
		});
		TextView textTitle = (TextView)headerView.findViewById(R.id.text_trailer_title);
		textTitle.setText(trailer.getTitle().replace("- YouTube", "").trim());
		FloatingActionButton fabPlay = (FloatingActionButton)headerView.findViewById(R.id.fab_play);
		fabPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(TrailerActivity.this,
						getVideoId(trailer.getUrl()), true, false);
				startActivity(intent);
			}
		});
		this.mListTrailer.addHeaderView(headerView);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_trailer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
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

package com.moviebomber.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.moviebomber.R;
import com.moviebomber.model.api.Trailer;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrailerActivity extends ActionBarActivity {

	public static final String EXTRA_TRAILER_LIST = "TRAILER";
	private static final String KEY = "AIzaSyAbR9V_oVGtZ4AEd3Er2ntbwh5zStfAW_s";

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	@InjectView(R.id.image_trailer)
	ImageView mImageTrailer;
	@InjectView(R.id.text_trailer_title)
	TextView mTextTitle;

	private List<Trailer> mTrailerList;
	private YouTubeThumbnailLoader mThumbnailLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trailer);
		ButterKnife.inject(this);
		this.setSupportActionBar(this.mToolbar);
		if (this.getSupportActionBar() != null) {
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			this.getSupportActionBar().setTitle("");
		}
		if (this.getIntent() != null) {
			this.mTrailerList = this.getIntent().getParcelableArrayListExtra(EXTRA_TRAILER_LIST);
		}
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

	class TrailerAdatper extends ArrayAdapter<Trailer> {
		TrailerAdatper(Context context, int resource, List<Trailer> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder)convertView.getTag();

			return convertView;
		}

		class ViewHolder {
			@InjectView(R.id.image_trailer)
			YouTubeThumbnailView mImageTrailer;
			@InjectView(R.id.text_trailer_title)
			TextView mTextTitle;

			ViewHolder(View itemView) {
				ButterKnife.inject(this, itemView);
			}
		}
	}

}

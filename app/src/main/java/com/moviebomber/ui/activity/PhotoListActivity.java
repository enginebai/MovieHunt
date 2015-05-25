package com.moviebomber.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;
import com.moviebomber.R;
import com.moviebomber.adapter.PhotoListAdapter;
import com.moviebomber.model.api.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PhotoListActivity extends ActionBarActivity implements
		ListBuddiesLayout.OnBuddyItemClickListener {

	public static final String EXTRA_POSTER = "POSTER";
	public static final String EXTRA_PHOTO_LIST = "PHOTO_LIST";

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	@InjectView(R.id.photo_background)
	ImageView mPhotoBackground;
	@InjectView(R.id.list_photo)
	ListBuddiesLayout mListPhoto;

	private List<Photo> mPhotoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window w = getWindow(); // in Activity's onCreate() for instance
//			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_photo_list);
		ButterKnife.inject(this);
		this.setSupportActionBar(this.mToolbar);
		if (this.getSupportActionBar() != null) {
			this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			this.getSupportActionBar().setTitle("");
		}
		Picasso.with(this).load(
				MainActivity.getResizePhoto(this, this.getIntent().getStringExtra(EXTRA_POSTER)))
				.into(this.mPhotoBackground);
		this.mPhotoList = this.getIntent().getParcelableArrayListExtra(EXTRA_PHOTO_LIST);
		PhotoListAdapter adapterLeft = new PhotoListAdapter(this,
				this.getResources().getDimensionPixelOffset(R.dimen.item_photo_height_tall),
				this.mPhotoList.subList(0, this.mPhotoList.size() / 2));
		PhotoListAdapter adapterRight = new PhotoListAdapter(this,
				this.getResources().getDimensionPixelOffset(R.dimen.item_photo_height_small),
				this.mPhotoList.subList(this.mPhotoList.size() / 2, this.mPhotoList.size()));
		this.mListPhoto.setAdapters(adapterLeft, adapterRight);
		this.mListPhoto.setOnItemClickListener(this);
	}


	@Override
	protected void onStart() {
		super.onStart();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
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
	public void onBuddyItemClicked(AdapterView<?> adapterView, View view, int i, int i2, long l) {
	}
}

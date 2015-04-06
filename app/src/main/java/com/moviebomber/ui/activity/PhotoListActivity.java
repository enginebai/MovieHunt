package com.moviebomber.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;
import com.moviebomber.R;
import com.moviebomber.adapter.PhotoListAdapter;
import com.moviebomber.model.api.Photo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PhotoListActivity extends ActionBarActivity implements
		ListBuddiesLayout.OnBuddyItemClickListener {

	public static final String EXTRA_PHOTO_LIST = "PHOTO_LIST";

	@InjectView(R.id.list_photo)
	ListBuddiesLayout mListPhoto;

	private List<Photo> mPhotoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_list);
		ButterKnife.inject(this);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_photo_list, menu);
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

	@Override
	public void onBuddyItemClicked(AdapterView<?> adapterView, View view, int i, int i2, long l) {
	}
}

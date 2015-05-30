package com.moviebomber.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.moviebomber.R;
import com.moviebomber.ui.fragment.MoviePageFragment;
import com.moviebomber.ui.fragment.NavigationDrawerFragment;
import com.rey.material.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	@InjectView(R.id.toolbar)
	Toolbar mToolbar;

	private InterstitialAd interstitial;

	/**
	 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window w = getWindow(); // in Activity's onCreate() for instance
//			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		this.setSupportActionBar(this.mToolbar);

		mNavigationDrawerFragment = (NavigationDrawerFragment)
				getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(
				R.id.navigation_drawer,
				this.mToolbar,
				(DrawerLayout) findViewById(R.id.drawer_layout));

//		this.interstitial = new InterstitialAd(this);
//		this.interstitial.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
//		// Create ad request.
//		AdRequest adRequest = new AdRequest.Builder().build();
//		// Begin loading your interstitial.
//		interstitial.loadAd(adRequest);
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
	public void onBackPressed() {
		super.onBackPressed();
//		if (interstitial.isLoaded()) {
//			interstitial.show();
//		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		Fragment fragment;
		String fragmentTag;
		switch (position) {
			case 0:
				fragment = MoviePageFragment.newInstance();
				fragmentTag = MoviePageFragment.class.getSimpleName();
				break;
			default:
				fragment = PlaceholderFragment.newInstance();
				fragmentTag = PlaceholderFragment.class.getSimpleName();
		}
		fragmentTransaction
				.replace(R.id.container, fragment, fragmentTag)
				.commit();
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setTitle(mTitle);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	public static String getResizePhoto(Context context, String path) {
		String extension = path.substring(path.lastIndexOf("."), path.length());
		StringBuilder photoUrl;
		photoUrl = new StringBuilder();
		photoUrl.append(context.getResources().getString(R.string.photo_root)).append("/")
				.append(path.substring(0, path.lastIndexOf("."))).append("_")
				.append(context.getResources().getString(R.string.photo_resize)).append(extension);
		return photoUrl.toString();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		@InjectView(R.id.button_blog)
		Button mButtonBlog;

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance() {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			return fragment;
		}
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_padding, container, false);
			ButterKnife.inject(this, rootView);
			mButtonBlog.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
							"http://enginebai.logdown.com"));
					startActivity(intent);
					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.container, MoviePageFragment.newInstance())
							.commit();
				}
			});
			return rootView;
		}
	}

}

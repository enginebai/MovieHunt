package com.moviebomber.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.moviebomber.ui.fragment.MovieListFragment;
import com.moviebomber.ui.fragment.MoviePageFragment;

/**
 * Created by engine on 15/3/29.
 */
public class MoviePagerAdapter extends FragmentStatePagerAdapter {
	private Context mContext;

	public MoviePagerAdapter(FragmentManager fm, Context c) {
		super(fm);
		this.mContext = c;
	}

	@Override
	public Fragment getItem(int position) {
		return MovieListFragment.newInstance(position);
	}

	@Override
	public int getCount() {
		return MoviePageFragment.TABS.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return this.mContext.getResources().getString(MoviePageFragment.TABS[position]);
	}
}

package com.moviebomber.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviebomber.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTabHost;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviePageFragment extends Fragment {

	private static final int[] TABS = {R.string.tab_movie_thisweek,
			R.string.tab_movie_intheater, R.string.tab_movie_comingsoon};
	@InjectView(R.id.tab_movie_list)
	MaterialTabHost mTabMoviewList;

	@InjectView(R.id.pager_movie)
	ViewPager mPagerMovie;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment MovieListFragment.
	 */
	public static MoviePageFragment newInstance() {
		MoviePageFragment fragment = new MoviePageFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public MoviePageFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_movie_page, container, false);
		this.initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		ButterKnife.inject(this, rootView);
		for (int i = 0; i < 3; i++) {
			this.mTabMoviewList.addTab(this.mTabMoviewList.newTab()
					.setText(getActivity().getResources().getString(TABS[i])));
		}
	}
}

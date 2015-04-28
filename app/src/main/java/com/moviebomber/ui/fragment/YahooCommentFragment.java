package com.moviebomber.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviebomber.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YahooCommentFragment extends Fragment {


	public YahooCommentFragment() {
		// Required empty public constructor
	}

	public static Fragment newInstance() {
		YahooCommentFragment fragment = new YahooCommentFragment();
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_yahoo_comment, container, false);
	}


}

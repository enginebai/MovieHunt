package com.moviebomber.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviebomber.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PttCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PttCommentFragment extends Fragment {

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment PttCommentFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PttCommentFragment newInstance() {
		PttCommentFragment fragment = new PttCommentFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public PttCommentFragment() {
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
		return inflater.inflate(R.layout.fragment_ptt_comment, container, false);
	}


}

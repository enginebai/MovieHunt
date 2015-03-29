package com.moviebomber.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.moviebomber.R;
import com.moviebomber.adapter.MovieListAdapter;
import com.moviebomber.model.api.ApiTask;
import com.moviebomber.model.api.MovieListItem;
import com.orhanobut.logger.Logger;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieListFragment extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_TAB_POSITION = "TAB_POSITION";

	@InjectView(R.id.list_movie)
	SuperRecyclerView mListMovie;

	private MovieListAdapter mAdapter;
	private int mCurrentTab = 0;
	private int mCurrentPage = 1;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param currentTabPosition current tab index
	 * @return A new instance of fragment MovieListFragment.
	 */
	public static MovieListFragment newInstance(int currentTabPosition) {
		MovieListFragment fragment = new MovieListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_TAB_POSITION, currentTabPosition);
		fragment.setArguments(args);
		return fragment;
	}

	public MovieListFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			this.mCurrentTab = getArguments().getInt(ARG_TAB_POSITION);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
		this.initView(rootView);
		this.loadMovies();
		return rootView;
	}

	private void initView(View rootView) {
		ButterKnife.inject(this, rootView);
		this.mListMovie.setRefreshingColorResources(
				R.color.primary,
				R.color.accent,
				android.R.color.holo_orange_light,
				android.R.color.holo_blue_bright);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		this.mListMovie.setLayoutManager(layoutManager);
		this.mListMovie.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {

				mCurrentPage = 1;
			}
		});
		this.mListMovie.setupMoreListener(new OnMoreListener() {
			@Override
			public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
				mListMovie.showMoreProgress();
				loadMovies();
				mListMovie.hideMoreProgress();
			}
		}, 1);
	}

	private void loadMovies() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = formatMovieListRequest();
		Logger.d(ApiTask.API_LOG_TAG, url);
		httpClient.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				List<MovieListItem> movieList = new ArrayList<>();
				if (mAdapter == null)
					mAdapter = new MovieListAdapter(movieList);
				mListMovie.setAdapter(mAdapter);
				mAdapter.getMovieList().addAll(movieList);
				mAdapter.notifyDataSetChanged();
				mCurrentPage++;
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	private String formatMovieListRequest() {
		// FIXME: replace the real url
		return "http://shareba.com/api.php";
	}

}

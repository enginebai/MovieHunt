package com.moviebomber.ui.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.moviebomber.R;
import com.moviebomber.adapter.MovieListAdapter;
import com.moviebomber.model.api.ApiTask;
import com.moviebomber.model.api.MovieListItem;
import com.moviebomber.model.utils.MovieListTab;
import com.moviebomber.model.utils.Query;
import com.orhanobut.logger.Logger;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	private boolean mLoadingMore = false;
	private boolean mShouldLoadMore = false;
	private MoviePageFragment.SortBy mSortBy = MoviePageFragment.SortBy.LASTEST;

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
		this.loadMovieList(true);
		return rootView;
	}

	private void initView(View rootView) {
		ButterKnife.inject(this, rootView);
		this.mListMovie.getSwipeToRefresh().setColorSchemeResources(
				R.color.primary,
				R.color.accent);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		this.mListMovie.setLayoutManager(layoutManager);
		this.mListMovie.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (mAdapter != null)
					mAdapter.getMovieList().clear();
				mCurrentPage = 1;
				loadMovieList(true);
			}
		});
		this.mListMovie.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (mShouldLoadMore && newState == RecyclerView.SCROLL_STATE_IDLE && !mLoadingMore) {
					mListMovie.showMoreProgress();
					loadMovieList(false);
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
				int visibleItemCount = layoutManager.getChildCount();
				int totalItemCount = layoutManager.getItemCount();
				mShouldLoadMore = (firstVisibleItem + visibleItemCount == totalItemCount);
			}
		});
	}

	public void loadMovieList(boolean cleanList) {
		this.mListMovie.getSwipeToRefresh().setRefreshing(true);
		if (cleanList && mAdapter != null) {
			this.mAdapter = null;
			this.mCurrentPage = 1;
		}
		String order;
		String url = "";
		if (this.mSortBy == MoviePageFragment.SortBy.LASTEST) {
			if (this.mCurrentTab == 2) // coming soon
				order = Query.OPERATOR_ASC;
			else
				order = Query.OPERATOR_DESC;
			url = formatMovieListRequest(Query.FIELD_RELEASE_DATE, order);
		} else if (this.mSortBy == MoviePageFragment.SortBy.OLDEST) {
			order = Query.OPERATOR_ASC;
			url = formatMovieListRequest(Query.FIELD_RELEASE_DATE, order);
		} else if (this.mSortBy == MoviePageFragment.SortBy.BOMBER) {
			order = Query.OPERATOR_DESC;
			url = formatMovieListRequest(Query.FIELD_GOOD_BOMBER, order);
		}
		Logger.d(ApiTask.API_LOG_TAG, url);
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//				Logger.json(ApiTask.API_LOG_TAG, response.toString());
				List<MovieListItem> movieList = new ArrayList<>();
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				if (mAdapter == null) {
					if (mCurrentTab == 2)
						mAdapter = new MovieListAdapter(getActivity(), new ArrayList<MovieListItem>(), false);
					else
						mAdapter = new MovieListAdapter(getActivity(), new ArrayList<MovieListItem>(), true);
					mListMovie.setAdapter(mAdapter);
				}
				mListMovie.getSwipeToRefresh().setRefreshing(false);
				mListMovie.hideMoreProgress();
				try {
					JSONArray objects = response.getJSONArray(ApiTask.RESPONSE_OBJECTS);
					if (objects.length() > 0) {
						for (int i = 0; i < objects.length(); i++) {
							MovieListItem item = gson.fromJson(objects.getJSONObject(i).toString(), MovieListItem.class);
							int totalBomber =
									(item.getGoodBomber() + item.getNormalBomber() + item.getBadBomber());
							item.setGoodRate((float)item.getGoodBomber() / (float)totalBomber);
							item.setNormalRate((float)item.getNormalBomber() / (float)totalBomber);
							item.setBadRate((float)item.getBadBomber() / (float)totalBomber);
							movieList.add(item);
						}
						mAdapter.getMovieList().addAll(movieList);
					} else {
						if (mAdapter.getMovieList().size() <= 0)
							Toast.makeText(getActivity(), getResources().getString(R.string.warn_no_movies),
									Toast.LENGTH_SHORT).show();
						else
							Toast.makeText(getActivity(), getResources().getString(R.string.warn_no_more_movies),
									Toast.LENGTH_SHORT).show();
						return;
					}
				}
				catch (JSONException e) {
					this.onFailure(statusCode, headers, e, response);
				}

				mAdapter.notifyDataSetChanged();
				mCurrentPage++;
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				// TODO: fail to get movie list
				Logger.e((Exception)throwable);
				Toast.makeText(mListMovie.getContext(),
						mListMovie.getContext().getResources().getString(R.string.error_fail_to_load_list),
						Toast.LENGTH_SHORT).show();
				mListMovie.getSwipeToRefresh().setRefreshing(false);
				mListMovie.hideMoreProgress();
			}
		});
	}

	private String formatMovieListRequest(String orderField, String orderDirection) {
		JSONObject q = new JSONObject();
		try {
			JSONArray filters = new JSONArray();
			JSONObject filter = new JSONObject();
			filter.put(Query.PARAM_NAME, Query.FIELD_RELEASE_STATUS);
			filter.put(Query.PARAM_OP, Query.OPERATOR_EQUAL);
			filter.put(Query.PARAM_VAL, MovieListTab.values()[this.mCurrentTab]);
			filters.put(filter);
			JSONArray orderBy = new JSONArray();
			JSONObject dateSort = new JSONObject();
			dateSort.put(Query.PARAM_FIELD, orderField);
			dateSort.put(Query.PARAM_DIRECTION, orderDirection);
			orderBy.put(dateSort);
			q.put(Query.PARAM_FILTERS, filters);
			q.put(Query.PARAM_ORDER_BY, orderBy);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		Resources res = this.getActivity().getResources();
		try {
			return String.format("%s%s%s?q=%s&%s=%d", res.getString(R.string.api_host),
					res.getString(R.string.api_root), res.getString(R.string.api_movie_list),
					URLEncoder.encode(q.toString(), "UTF8"),
					Query.PARAM_PAGE, this.mCurrentPage);
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public void setSortBy(MoviePageFragment.SortBy mSortBy) {
		this.mSortBy = mSortBy;
	}
}

package com.moviebomber.ui.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.pavlospt.CircleView;
import com.moviebomber.R;
import com.moviebomber.model.api.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PttCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PttCommentFragment extends Fragment {

	public static final String EXTRA_PTT_COMMENTS = "PTT_COMMENTS";

	@InjectView(R.id.list_comment)
	ListView mListComment;

	private List<Article> mArticleList;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *f
	 * @return A new instance of fragment PttCommentFragment.
	 */
	public static PttCommentFragment newInstance(ArrayList<Article> articleList) {
		PttCommentFragment fragment = new PttCommentFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList(EXTRA_PTT_COMMENTS, articleList);
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
			this.mArticleList = getArguments().getParcelableArrayList(EXTRA_PTT_COMMENTS);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_ptt_comment, container, false);
		initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		ButterKnife.inject(this, rootView);
		this.mListComment.setAdapter(new CommentAdapter(getActivity(), R.layout.item_ptt_comment, mArticleList));
		this.setupHeader();
	}

	private void setupHeader() {
		int goodBomber = 0;
		int normalBomber = 0;
		int badBomber = 0;
		for (Article a : mArticleList) {
			if (a.getBomberStatus().equals(Article.BomberStatus.GOOD.toString()))
				goodBomber++;
			else if (a.getBomberStatus().equals(Article.BomberStatus.NORMAL.toString()))
				normalBomber++;
			else if (a.getBomberStatus().equals(Article.BomberStatus.BAD.toString()))
				badBomber++;
		}
		View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.header_ptt_comment, null);
		CircleView goodBomberView = (CircleView)headerView.findViewById(R.id.circle_good_bomber);
		CircleView normalBomberView = (CircleView)headerView.findViewById(R.id.circle_normal_bomber);
		CircleView badBomberView = (CircleView)headerView.findViewById(R.id.circle_bad_bomber);
		Resources res = getActivity().getResources();
		goodBomberView.setTitleText(String.valueOf(goodBomber) + res.getString(R.string.text_good_bomber));
		normalBomberView.setTitleText(String.valueOf(normalBomber) + res.getString(R.string.text_normal_bomber));
		badBomberView.setTitleText(String.valueOf(badBomber) + res.getString(R.string.text_bad_bomber));
		this.mListComment.addHeaderView(headerView);
	}

	class CommentAdapter extends ArrayAdapter<Article> {

		public CommentAdapter(Context context, int resource, List<Article> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ptt_comment, parent, false);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder)convertView.getTag();
			Article article = this.getItem(position);
			holder.mTextTitle.setText(article.getTitle());
			return convertView;
		}

		class ViewHolder {
			@InjectView(R.id.text_article_title)
			TextView mTextTitle;

			ViewHolder(View itemView) {
				ButterKnife.inject(this, itemView);
			}
		}
	}
}

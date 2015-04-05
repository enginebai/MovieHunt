package com.moviebomber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jpardogo.listbuddies.lib.adapters.CircularLoopAdapter;
import com.moviebomber.R;
import com.moviebomber.model.api.PhotoList;
import com.moviebomber.utils.ScaleToFitWidhtHeigthTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by engine on 15/4/5.
 */
public class PhotoListAdapter extends CircularLoopAdapter {

	private List<PhotoList> mPhotoUrlList = new ArrayList<>();
	private Context mContext;
	private int mRowHeight;

	public PhotoListAdapter(Context context, int rowHeight, List<PhotoList> photoUrlList) {
		this.mContext = context;
		this.mRowHeight = rowHeight;
		this.mPhotoUrlList = photoUrlList;
	}
	@Override
	protected int getCircularCount() {
		return this.mPhotoUrlList.size();
	}

	@Override
	public PhotoList getItem(int position) {
		return this.mPhotoUrlList.get(this.getCircularPosition(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_photo, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.mImage.setMinimumHeight(mRowHeight);
		String url = this.getItem(position).getUrl();
		url = url.replace("mpho3", "mpho2");
		Picasso.with(this.mContext).load(url).transform(
				new ScaleToFitWidhtHeigthTransform(mRowHeight, true)).into(holder.mImage);
		return convertView;
	}

	class ViewHolder {
		@InjectView(R.id.image_photo_item)
		ImageView mImage;

		ViewHolder(View itemView) {
			ButterKnife.inject(this, itemView);
		}
	}
}

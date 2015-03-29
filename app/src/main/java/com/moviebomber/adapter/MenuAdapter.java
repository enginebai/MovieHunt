package com.moviebomber.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviebomber.R;
import com.moviebomber.model.MenuSection;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by engine on 15/3/29.
 */
public class MenuAdapter extends ArrayAdapter<MenuSection> {

	public MenuAdapter(Context context, int resource, List<MenuSection> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu_section,
					parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder)convertView.getTag();
		MenuSection menu = this.getItem(position);
		Drawable icon = this.getContext().getResources().getDrawable(menu.getIconRes());
		icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
		holder.mImageIcon.setBackground(icon);
		holder.mTextMenu.setText(menu.getTitle());
		return convertView;
	}

	class ViewHolder {
		@InjectView(R.id.image_menu_icon)
		ImageView mImageIcon;

		@InjectView(R.id.text_menu)
		TextView mTextMenu;

		ViewHolder(View itemView) {
			ButterKnife.inject(this, itemView);
		}
	}
}

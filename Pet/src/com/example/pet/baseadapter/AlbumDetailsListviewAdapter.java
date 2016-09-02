package com.example.pet.baseadapter;

import java.util.List;

import com.example.pet.R;
import com.example.pet.lei.AlbumDetailsListview;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class AlbumDetailsListviewAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context;
	List<AlbumDetailsListview> list;
	int item;
	int[] id;

	public AlbumDetailsListviewAdapter() {
	}

	public AlbumDetailsListviewAdapter(Context context,
			List<AlbumDetailsListview> list, int item, int[] id) {
		super();
		this.context = context;
		this.list = list;
		this.item = item;
		this.id = id;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(item, null);
		}
		TextView time = (TextView) convertView.findViewById(id[0]);
		GridView gridView = (GridView) convertView.findViewById(id[1]);
		AlbumDetailsListview albumDetails = list.get(position);
		time.setText(albumDetails.getTime());
		GridAdapter adapter = new GridAdapter(context, albumDetails.getList(),
				R.layout.gridview_item_album_details,
				R.id.gridview_item_album_details);
		gridView.setAdapter(adapter);
		return convertView;
	}

}

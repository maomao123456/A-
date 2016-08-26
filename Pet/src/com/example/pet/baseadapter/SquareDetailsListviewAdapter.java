package com.example.pet.baseadapter;

import java.util.List;

import com.example.pet.R;
import com.example.pet.lei.SquareDetailsListview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SquareDetailsListviewAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<SquareDetailsListview> list;
	int item;
	int[] id;
	
	public SquareDetailsListviewAdapter() {
		// TODO Auto-generated constructor stub
	}
	public SquareDetailsListviewAdapter(Context context,List<SquareDetailsListview> list,int item,int[] id){
		super();
		this.context=context;
		this.list=list;
		this.item=item;
		this.id=id;
		inflater=LayoutInflater.from(context);
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
		if(convertView==null){
			convertView=inflater.inflate(item, null);
		}
		ImageView head=(ImageView)convertView.findViewById(id[0]);
		TextView name=(TextView)convertView.findViewById(id[1]);
		TextView words=(TextView)convertView.findViewById(id[2]);
		TextView time=(TextView)convertView.findViewById(id[3]);
		SquareDetailsListview details=list.get(position);
		head.setImageResource(details.getHead());
		name.setText(details.getName());
		words.setText(details.getWords());
		time.setText(details.getTime());
		return convertView;
	}

}

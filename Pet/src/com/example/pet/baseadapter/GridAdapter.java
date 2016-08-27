package com.example.pet.baseadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.pet.lei.SquareGridview;


public class GridAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<SquareGridview> list1;
	int item;
	int id;
	
	public GridAdapter(){
	}
	public GridAdapter(Context context,List<SquareGridview> list1,int item,int id){
		super();
		this.context=context;
		this.list1=list1;
		this.item=item;
		this.id=id;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list1.get(position);
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
		ImageView img=(ImageView)convertView.findViewById(id);
		SquareGridview gridview=list1.get(position);
		img.setImageResource(gridview.getImage());
		
		return convertView;
	}
}

package com.example.baseadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lei.SquareGridview;
import com.example.lei.SquareListview;
import com.example.pet.R;

public class SquareAdapter extends BaseAdapter{
	LayoutInflater inflater;
	Context context;
	List<SquareListview> list;
	int item;
	int[] id;
	
	public SquareAdapter(){
	}
	public SquareAdapter(Context context,List<SquareListview> list,int item,int[] id){
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
		ImageView little_head=(ImageView)convertView.findViewById(id[0]);
		TextView name=(TextView)convertView.findViewById(id[1]);
		TextView time=(TextView)convertView.findViewById(id[2]);
		TextView guanzhu=(TextView)convertView.findViewById(id[3]);
		TextView miaoshu=(TextView)convertView.findViewById(id[4]);
		GridView gridView=(GridView)convertView.findViewById(id[5]);
		ImageView dingwei=(ImageView)convertView.findViewById(id[6]);
		TextView address=(TextView)convertView.findViewById(id[7]);
		TextView collections=(TextView)convertView.findViewById(id[8]);
		TextView pinglun=(TextView)convertView.findViewById(id[9]);
		SquareListview square=list.get(position);
		little_head.setImageResource(square.getLittle_head());
		name.setText(square.getName());
		time.setText(square.getTime());
		guanzhu.setText(square.getGuanzhu());
		miaoshu.setText(square.getMiaoshu());
		
		dingwei.setImageResource(square.getDingwei());
		address.setText(square.getAddress());
		collections.setText(square.getCollections()+"");
		pinglun.setText(square.getPinglun()+"");
		GridAdapter adapter=new GridAdapter(context,square.getList() ,
				R.layout.gridview_item_square, R.id.gridview_item_grid);
		gridView.setAdapter(adapter);
		return convertView;
	}

}

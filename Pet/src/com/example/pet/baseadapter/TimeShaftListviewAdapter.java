package com.example.pet.baseadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.R;
import com.example.pet.lei.TimeShaftListview;

public class TimeShaftListviewAdapter extends BaseAdapter{

	LayoutInflater inflater;
	Context context;
	List<TimeShaftListview> list;
	int item;
	int[] id;
	
	public TimeShaftListviewAdapter(){
	}
	public TimeShaftListviewAdapter(Context context,List<TimeShaftListview> list,int item,int[] id){
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
		TextView time=(TextView)convertView.findViewById(id[1]);
		TextView word=(TextView)convertView.findViewById(id[2]);
		GridView gridView=(GridView)convertView.findViewById(id[3]);
		TimeShaftListview timeShaft=list.get(position);
		head.setImageResource(timeShaft.getHead());
		time.setText(timeShaft.getTime());
		word.setText(timeShaft.getWord());
		GridAdapter adapter=new GridAdapter(context,timeShaft.getList() ,
				R.layout.gridview_item_timeshaft, R.id.gridview_item_timeshaft);
		gridView.setAdapter(adapter);
		if(position==0){
			time.setTextColor(convertView.getResources().getColor(R.color.text_3c));
			word.setTextColor(convertView.getResources().getColor(R.color.text_3c));
		}
		int numb=timeShaft.getList().size();
		gridView.setNumColumns(numb);
		return convertView;
	}

}


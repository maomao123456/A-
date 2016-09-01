package com.example.pet.baseadapter;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.lei.SquareGridview;
import com.example.pet.lei.SquareListview;

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
	
	TextView guanzhu;
	ImageView little_head;
	TextView name;
	TextView time;
	TextView miaoshu;
	GridView gridView;
	ImageView dingwei;
	TextView address;
	TextView collections;
	TextView pinglun;
	SquareListview square;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=inflater.inflate(item, null);
		}
		little_head=(ImageView)convertView.findViewById(id[0]);
		name=(TextView)convertView.findViewById(id[1]);
		time=(TextView)convertView.findViewById(id[2]);
		guanzhu=(TextView)convertView.findViewById(id[3]);
		miaoshu=(TextView)convertView.findViewById(id[4]);
		gridView=(GridView)convertView.findViewById(id[5]);
		dingwei=(ImageView)convertView.findViewById(id[6]);
		address=(TextView)convertView.findViewById(id[7]);
		collections=(TextView)convertView.findViewById(id[8]);
		pinglun=(TextView)convertView.findViewById(id[9]);
		square=list.get(position);
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
		gridView.setNumColumns(square.getList().size());
		/*guanzhu.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {//对关注按钮的监听
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					Toast.makeText(context, "0000", Toast.LENGTH_SHORT).show();
					guanzhu();
					break;
				case 1:
					Toast.makeText(context, "2222", Toast.LENGTH_SHORT).show();
					guanzhu();
					break;
				}
			}
		});*/
		return convertView;
	}
	/**
	 * 判断关注情况
	 *//*
	public void guanzhu(){
		if(guanzhu.getText().toString().equals("+关注")){
			guanzhu.setText("已关注");
		}else{
			AlertDialog alertDialog;
			Builder builder;
			builder=new AlertDialog.Builder(context);
			builder.setMessage("你真的要取消对我的关注吗？");
			builder.setPositiveButton("是的", onClickListener);
			builder.setNegativeButton("我再想想", onClickListener);
			alertDialog=builder.create();
			alertDialog.show();
		}
	}*/
	/**
	 * 对是否取消关注的监听
	 *//*
	DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			switch(arg1){
			case -1:
				guanzhu.setText("+关注");
				Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
				break;
			case -2:
				break;
			}
		}
	};*/
}

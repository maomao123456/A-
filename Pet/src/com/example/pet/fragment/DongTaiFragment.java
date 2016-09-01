package com.example.pet.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.pet.NewAlbumActivity;
import com.example.pet.R;
import com.example.pet.TimeShaftActivity;

public class DongTaiFragment extends Fragment{
	View view;
	ScrollView scrollView;
	GridView gridview;
	SimpleAdapter adapter;
	String[] keys={"image","text"};
	int[] viewIds={R.id.album_dongtai,R.id.album_text_dongtai};
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ImageView head,timeShaft;
	TextView new_albums;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=(View)inflater.inflate(R.layout.fragment_dongtai, null);
		initView();
		return view;
	}
	/**
	 * 找id，设监听
	 */
	public void initView(){
		gridview=(GridView)view.findViewById(R.id.gridview_dongtai);
		scrollView=(ScrollView)view.findViewById(R.id.scrollview_dongtai);
		gridview.setAdapter(new GridAdapter(getActivity())); 
		head=(ImageView)view.findViewById(R.id.head_dongtai);
		head.setImageResource(R.drawable.dongtai_head);
		timeShaft=(ImageView)view.findViewById(R.id.enter_timeshaft_dongtai);
		new_albums=(TextView)view.findViewById(R.id.new_albums_dongtai);
		timeShaft.setOnClickListener(clickListener);
		new_albums.setOnClickListener(clickListener);
	}
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.enter_timeshaft_dongtai:
				startActivity(new Intent(getActivity(),TimeShaftActivity.class));
				break;
			case R.id.new_albums_dongtai:
				startActivity(new Intent(getActivity(),NewAlbumActivity.class));
				break;
			}
		}
	};
	/**
	 * 以下代码为scrollView与gridView不冲突的设置代码
	 */
	String[] texts={"我和你","美好时光","萌萌哒","嘿,别惹我","我就叫高兴","我害羞"};
	int[] imgs={R.drawable.album1_dongtai,R.drawable.album2_dongtai,
			R.drawable.album3_dongtai,R.drawable.album4_dongtai,
			R.drawable.album5_dongtai,R.drawable.album6_dongtai};
	 private class GridAdapter extends BaseAdapter{ 
		 
	        Activity context; 
	        public GridAdapter(Activity context){ 
	            this.context=context; 
	        } 
	        @Override 
	        public int getCount() { 
	            // TODO Auto-generated method stub 
	            return texts.length; 
	        } 
	 
	        @Override 
	        public Object getItem(int position) { 
	            // TODO Auto-generated method stub 
	            return null; 
	        } 
	 
	        @Override 
	        public long getItemId(int position) { 
	            // TODO Auto-generated method stub 
	            return 0; 
	        } 
	 
	        @Override 
	        public View getView(int position, View convertView, ViewGroup parent) { 
	            // TODO Auto-generated method stub 
	            ViewHolder holder=null; 
	            if(convertView==null){ 
	                convertView=context.getLayoutInflater().inflate(R.layout.gridview_item_dongtai, null); 
	                holder=new ViewHolder(); 
	                holder.image=(ImageView)convertView.findViewById(R.id.album_dongtai); 
	                holder.text=(TextView)convertView.findViewById(R.id.album_text_dongtai); 
	                convertView.setTag(holder); 
	            } 
	            else{ 
	                holder=(ViewHolder)convertView.getTag(); 
	               
	            } 
	            holder.image.setImageResource(imgs[position]); 
	            holder.text.setText(texts[position]); 
	            return convertView; 
	        } 
	        class ViewHolder { 
	            ImageView image; 
	            TextView text; 
	        } 
	         
	    } 
}

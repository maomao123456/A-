package com.example.pet.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.pet.DianpuActivity;
import com.example.pet.R;

public class ResourceFragment extends Fragment {
	ImageView imageview1, imageView2;
	ListView listView;
	View headView;
	SimpleAdapter simpleAdapter;
	Button button;
	TextView textView1, textView2;

	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { // TODO Auto-generated method
	 * stub View view=inflater.inflate(R.layout.activity_resource, null);
	 * imageview=(ImageView)view.findViewById(R.id.dianpu);
	 * imageview.setOnClickListener(onClickListener); return view; }
	 */

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_resource_pet, null);
		listView = (ListView) view.findViewById(R.id.listview);
		headView = (View) inflater.inflate(R.layout.activity_headview_resource,
				null);
		listView.addHeaderView(headView);
		String[] from = {"imageview","name","data","button","imageview","imageview1","textview", "addess","view1","view2",};
		int[] to = {R.id.tximageview,R.id.name1,R.id.data,R.id.button,R.id.imageview1,R.id.imageview2,R.id.textview,R.id.addess,R.id.view1,R.id.view2};
		getData();
		simpleAdapter = new SimpleAdapter(getActivity(), getData(),	
				R.layout.activity_simpletadapter_resource, from, to);
		listView.setAdapter(simpleAdapter);
		listView.setOnClickListener(onClickListener);
		return view;
	};
	
	public List<? extends Map<String, ?>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("imageview", R.drawable.chat_user_icon);
			map.put("name", "苏菲16177729");
			map.put("data", "12天前");
			map.put("button", "求包养");
			map.put("imageview", R.drawable.homepage_personage_pic1);
			map.put("imageview1", R.drawable.homepage_personage_pic2);
			map.put("textview", "		哈士奇性格多变，有的极端胆小，有的极端暴力，进入大陆和家庭的哈士奇，都已经没有了这种极端的性格，比较温顺，是一种流行于全球的宠物犬。与金毛犬、拉布拉多并列为三大无攻击型犬类。");
			map.put("addess","来自重庆		渝中区|上清寺");
			map.put("view1", "留言");
			map.put("view2", "2");
			list.add(map);
			
			map=new HashMap<String, Object>();
			
			map.put("imageview", R.drawable.chat_user_icon);
			map.put("name", "安度因16177729");
			map.put("data", "5天前");
			map.put("button", "求包养");
			map.put("imageview", R.drawable.homepage_personage_pic1);
			map.put("imageview1", R.drawable.homepage_personage_pic2);
			map.put("textview", "		哈士奇性格多变，有的极端胆小，有的极端暴力，进入大陆和家庭的哈士奇，都已经没有了这种极端的性格，比较温顺，是一种流行于全球的宠物犬。与金毛犬、拉布拉多并列为三大无攻击型犬类。");
			map.put("addess","来自四川		南区|");
			map.put("view1", "留言");
			map.put("view2", "3");
			list.add(map);
		return list;
		
		
	}
	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), DianpuActivity.class);
			startActivity(intent);
		}
	};

}

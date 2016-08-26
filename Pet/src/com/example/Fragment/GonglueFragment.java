package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pet.R;
import com.example.pet.R.drawable;
import com.example.pet.R.id;
import com.example.pet.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class GonglueFragment extends Fragment {
	ListView listView;
	SimpleAdapter simpleadapter;
	public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_gonglue, null);
		listView = (ListView) view.findViewById(R.id.listview);
		getdata();
		String[] from = { "imag", "title1", "title2", "title3", "imagJt" };
		int[] to = { R.id.imageview, R.id.textview1, R.id.textview2,
				R.id.textview3, R.id.imageviewJt };
		simpleadapter = new SimpleAdapter(getActivity(), getdata(),
				R.layout.activity_simpleadapter_gonglue, from, to);
		listView.setAdapter(simpleadapter);
		return view;
	};
	
		

	public List<? extends Map<String, ?>> getdata() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imag", R.drawable.dog1);
		map.put("title1", "金毛寻回犬");
		map.put("title2", "Canis lupus familiaris");
		map.put("title3", "夏天是金毛犬最难熬的季节，金毛...");
		map.put("imagJt", R.drawable.rightjt);
		list.add(map);

		map=new HashMap<String, Object>();
		map.put("imag", R.drawable.dog2);
		map.put("title1", "西伯利亚雪橇犬");
		map.put("title2", "Siberian husky");
		map.put("title3", "西伯利亚雪橇犬与其他猎犬不一样...");
		map.put("imagJt", R.drawable.rightjt);
		list.add(map);
		
		map=new HashMap<String, Object>();
		map.put("imag", R.drawable.dog3);
		map.put("title1", "萨摩耶犬");
		map.put("title2", "Samoyed");
		map.put("title3", "清洗注意事项，基础训练，玩具管理...");
		map.put("imagJt", R.drawable.rightjt);
		list.add(map);
		
		map=new HashMap<String, Object>();
		map.put("imag", R.drawable.dog4);
		map.put("title1", "贵宾犬");
		map.put("title2", "Poodle");
		map.put("title3", "日常养护，狗粮益生菌选择，喂养技巧...");
		map.put("imagJt", R.drawable.rightjt);
		list.add(map);
		
		map=new HashMap<String, Object>();
		map.put("imag", R.drawable.dog5);
		map.put("title1", "比雄犬");
		map.put("title2", "Bichon Frise");
		
		map.put("title3", " ");
		map.put("imagJt", R.drawable.rightjt);
		list.add(map);
		return list;
	}

}

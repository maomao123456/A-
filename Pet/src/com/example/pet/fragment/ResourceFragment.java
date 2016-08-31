package com.example.pet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pet.DianpuActivity;
import com.example.pet.R;

public class ResourceFragment  extends Fragment{
	ImageView imageview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.activity_resource, null);
		imageview=(ImageView)view.findViewById(R.id.dianpu);
		imageview.setOnClickListener(onClickListener);
		return view;
	}
OnClickListener onClickListener=new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(getActivity(), DianpuActivity.class);
		startActivity(intent);
	}
};
		
}

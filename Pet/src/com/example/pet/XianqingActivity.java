package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet.classes.SysApplication;

public class XianqingActivity extends Activity {
	ImageView imageview;
	TextView tetview;
	CheckBox textview1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangqing);
		SysApplication.getInstance().addActivity(this);
		
		imageview=(ImageView)findViewById(R.id.imageview);
		textview1=(CheckBox)findViewById(R.id.sc);
		imageview.setOnClickListener(onClickListener);
		tetview=(TextView) findViewById(R.id.dianping111);
		tetview.setOnClickListener(onClickListener);
		textview1.setOnClickListener(onClickListener);
		SharedPreferences pf = getSharedPreferences("pet_shoucang",
				MODE_PRIVATE);
		boolean cdSc = pf.getBoolean("shoucang", false);
		if (cdSc) {
			textview1.setChecked(true);
		} else {
			textview1.setChecked(false);
		}
	}
	OnClickListener onClickListener=new OnClickListener() {
	
		
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.dianping111:
				Intent intent=new Intent(XianqingActivity.this, DianpingActivity.class);
				startActivity(intent);
			
				break;
			case R.id.imageview:
				Intent intent1=new Intent(XianqingActivity.this, MainActivity.class);
				startActivity(intent1);
				break;
				
			case R.id.sc:
				//isColleation();
				/*Editor editor=getSharedPreferences("pet_shoucang", MODE_PRIVATE).edit();
				if(textview1.isChecked()){
					editor.putBoolean("shoucang", true);
					Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT).show();
				}else{
					editor.putBoolean("shoucang", false);
					Toast.makeText(getApplicationContext(), "取消收藏", Toast.LENGTH_SHORT).show();
				}editor.commit();*/
				break;
				

			default:
				break;
				
			}
			
			
		}
	};
	
	/*boolean state = false;// 是否收藏，默认不收藏
	private void isColleation(){
		Drawable collection = getResources().getDrawable(R.drawable.ratingbarshixin);
		Drawable notCollection = getResources().getDrawable(R.drawable.ratingbarshixin);
		if(state == false){
			
			collection.setBounds(collection.getMinimumWidth(), collection.getMinimumHeight(), 50, 50);
			textview1.setCompoundDrawables(collection, null, null, null);
			state = true;
			Toast.makeText(XianqingActivity.this, "收藏+1", Toast.LENGTH_SHORT).show();
		} else {
			notCollection.setBounds(collection.getMinimumWidth(), collection.getMinimumHeight(), 50, 50);
			textview1.setCompoundDrawables(notCollection, null, null, null);
			state = false;
			Toast.makeText(XianqingActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
		}
	}*/
}

package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;

public class XianqingActivity extends Activity {
	ImageView imageview;
	TextView tetview,dogName;
	CheckBox textview1;
	int numba=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangqing);
		SysApplication.getInstance().addActivity(this);
		if(getIntent().getExtras()!=null){
			numba=getIntent().getExtras().getInt("dogName");
		}else{
		}
		dogName=(TextView)findViewById(R.id.dog_name);
		imageview=(ImageView)findViewById(R.id.imageview);
		textview1=(CheckBox)findViewById(R.id.sc);
		imageview.setOnClickListener(onClickListener);
		tetview=(TextView) findViewById(R.id.dianping111);
		tetview.setOnClickListener(onClickListener);
		textview1.setOnClickListener(onClickListener);
		getDogName();
		SharedPreferences pf=getSharedPreferences("pet_shoucang", MODE_PRIVATE);
		boolean ifSc=pf.getBoolean("shoucang"+numba, false);
		if(ifSc){
			textview1.setChecked(true);
		}else{
			textview1.setChecked(false);
		}
		
	}

	public void getDogName() {
		switch (numba) {
		case 1:
			dogName.setText("金毛寻回犬");
			break;
		case 2:
			dogName.setText("西伯利亚雪橇犬");
			break;
		case 3:
			dogName.setText("萨摩耶犬");
			break;
		case 4:
			dogName.setText("贵宾犬");
			break;
		case 5:
			dogName.setText("比雄犬");
			break;

		default:
			break;
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
				SharedPreferences pf=getSharedPreferences("pet_shoucang", MODE_PRIVATE);
				Editor editor=getSharedPreferences("pet_shoucang", MODE_PRIVATE).edit();
				if(textview1.isChecked()){
					int a=1;
					int b=pf.getInt("shoucang", 0);
					editor.putInt("shoucang", b+a);
					editor.putBoolean("shoucang"+numba, true);
					Toast.makeText(XianqingActivity.this, "收藏+1", Toast.LENGTH_SHORT).show();
				}else{
					int a=-1;
					int b=pf.getInt("shoucang", 0);
					editor.putInt("shoucang", b+a);
					editor.putBoolean("shoucang"+numba, false);
					Toast.makeText(XianqingActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
				}
				editor.commit();
				
				break;
			default:
				break;
			}
		}
	};
	
	
}

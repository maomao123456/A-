package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class NewAlbumActivity extends Activity{
	TextView quxiao,finish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_album_dongtai);
		init();
	}
	public void init(){
		quxiao=(TextView)findViewById(R.id.quxiao_new_albums);
		finish=(TextView)findViewById(R.id.finish_new_albums);
		quxiao.setOnClickListener(clickListener);
		finish.setOnClickListener(clickListener);
	}
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.quxiao_new_albums:
				startActivity(new Intent(NewAlbumActivity.this,MainActivity.class));
				NewAlbumActivity.this.finish();
				break;
			case R.id.finish_new_albums:
				Toast.makeText(NewAlbumActivity.this, "完成", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
}

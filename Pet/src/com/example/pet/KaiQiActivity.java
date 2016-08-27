package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class KaiQiActivity extends Activity{
	ImageView kaiqi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaiqi);
		kaiqi=(ImageView)findViewById(R.id.kaiqi);
		setImg();
		doWait();
	}
	public void setImg(){
		int[] image={R.drawable.kaiqi3,R.drawable.kaiqi2,R.drawable.kaiqi1,R.drawable.kaiqi3};
		int a=(int)((Math.random())*3);
		kaiqi.setImageResource(image[a]);
		final AnimationSet animationSet=new AnimationSet(true);
		ScaleAnimation scaleAnimation=new ScaleAnimation(1f, 0, 1f, 0,
				Animation.RELATIVE_TO_SELF,0.5f,//初始位置开始缩放X轴
				Animation.RELATIVE_TO_SELF,0.5f);//初始缩放位置 Y轴
		scaleAnimation.setDuration(1000);
		scaleAnimation.setRepeatCount(1);
		animationSet.addAnimation(scaleAnimation);
		Handler mHandler = new Handler(); 
		mHandler.postDelayed(new Runnable() {
			 @Override public void run() {
				 kaiqi.startAnimation(animationSet);
			} },1200); 
	}
	public void doWait(){
		SharedPreferences pf=getSharedPreferences("pet", MODE_PRIVATE);
		int a=pf.getInt("guide", 0);
		int b=pf.getInt("login", 0);
		int c=pf.getInt("kills", 1);
		if(a==0||b==0||c==1){
			Handler mHandler = new Handler(); 
			mHandler.postDelayed(new Runnable() {
				 @Override public void run() {
					Intent intent = new Intent(KaiQiActivity.this,
							MainActivity.class);
					startActivity(intent);
					KaiQiActivity.this.finish();
				} },2000); 
		}else{
		Intent	intent2 = new Intent(KaiQiActivity.this,
					MainActivity.class);
			startActivity(intent2);
			KaiQiActivity.this.finish();
		}
		
	}

}

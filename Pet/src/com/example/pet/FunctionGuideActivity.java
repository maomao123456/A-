package com.example.pet;

import com.example.pet.classes.SysApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewFlipper;

public class FunctionGuideActivity extends Activity {

	ViewFlipper functionViewFlipper;
	LinearLayout functionBG;
	RadioGroup functionGuide;
	RadioButton functionShop, functionStrategy;
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function_introduction);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	private void initView() {
		functionViewFlipper = (ViewFlipper) findViewById(R.id.function_view);
		functionBG = (LinearLayout) findViewById(R.id.function_bg);
		functionGuide = (RadioGroup) findViewById(R.id.function_guide);
		functionShop = (RadioButton) findViewById(R.id.function_shop);
		functionStrategy = (RadioButton) findViewById(R.id.function_strategy);
		
		//触发点击
		functionGuide.setOnCheckedChangeListener(changeListener);//单选按钮
		functionBG.setOnTouchListener(listener);//在单选按钮范围外的滑动事件
	}

	// 布局滑动监听事件
	OnTouchListener listener = new OnTouchListener() {
		@SuppressLint("ClickableViewAccessibility")
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){//当手指按下的时候
				x1 = event.getX();
				y1 = event.getY();
			}
			if(event.getAction() == MotionEvent.ACTION_UP){//当手机离开的时候
				x2 = event.getX();
				y2 = event.getY();
				if(y1 - y2 > 50){
					//向上滑动
				} else if(y2 - y1 > 50){
					//向下滑动
				} else if(x1 - x2 > 20){
					//向左滑动 下一页
					if(functionViewFlipper.getDisplayedChild() == 0){
						functionViewFlipper.setDisplayedChild(1);
						functionStrategy.setChecked(true);
					} else if(functionViewFlipper.getDisplayedChild() == 1){
						
					}
				} else if(x2 - x1 > 20){
					//向右滑动 上一页
					if(functionViewFlipper.getDisplayedChild() == 0){
						
					} else if(functionViewFlipper.getDisplayedChild() == 1){
						functionViewFlipper.setDisplayedChild(0);
						functionShop.setChecked(true);
					}
				}
			}
			return true;
		}
	};
	
	//圆点单选按钮的点击事件
	OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(RadioGroup group, int checkId) {
			switch(checkId){
			case R.id.function_shop:
				functionViewFlipper.setDisplayedChild(0);
				break;
			case R.id.function_strategy:
				functionViewFlipper.setDisplayedChild(1);
				break;
				
			default:
				break;
			}
		}
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), AboutPetsActivity.class);
			startActivity(intent);
			FunctionGuideActivity.this.finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}

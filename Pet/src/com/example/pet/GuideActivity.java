package com.example.pet;

import com.example.pet.classes.SysApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class GuideActivity extends Activity implements OnClickListener {
	ViewFlipper viewFlipper;
	LinearLayout buju;
	RadioGroup radioGroup;
	RadioButton bt1, bt2, bt3;
	TextView startPet;
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		panDuan();
		setContentView(R.layout.activity_guide);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	/**
	 * 首次安装 guide为1
	 */
	public void panDuan() {
		SharedPreferences pf = getSharedPreferences("pet", MODE_PRIVATE);
		int numb = pf.getInt("guide", 0);
		if (numb == 0) {
			Editor editor = getSharedPreferences("pet", MODE_PRIVATE).edit();
			editor.putInt("guide", 1);
			editor.commit();
		} else {
			Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
			startActivity(intent);
			GuideActivity.this.finish();
		}

	}

	/**
	 * 通过ID找控件
	 */
	public void initView() {
		viewFlipper = (ViewFlipper) findViewById(R.id.guide_viewflipper);
		buju = (LinearLayout) findViewById(R.id.guide_buju);
		radioGroup = (RadioGroup) findViewById(R.id.guide_radio_group);
		bt1 = (RadioButton) findViewById(R.id.guide_rd_bt1);
		bt2 = (RadioButton) findViewById(R.id.guide_rd_bt2);
		bt3 = (RadioButton) findViewById(R.id.guide_rd_bt3);
		startPet = (TextView) findViewById(R.id.guide_start_pet);
		startPet.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);
		buju.setOnTouchListener(onTouchListener);
	}

	/**
	 * 布局滑动监听事件
	 */
	OnTouchListener onTouchListener = new OnTouchListener() {
		@SuppressLint("ClickableViewAccessibility")
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 当手指按下的时候
				x1 = event.getX();
				y1 = event.getY();
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// 当手指离开的时候
				x2 = event.getX();
				y2 = event.getY();
				if (y1 - y2 > 50) {// 向上滑
				} else if (y2 - y1 > 50) {// 向下滑
				} else if (x1 - x2 > 20) {// 向左滑 下一页
					if (viewFlipper.getDisplayedChild() == 0) {
						viewFlipper.setDisplayedChild(1);
						bt2.setChecked(true);
						startPet.setVisibility(View.GONE);
					} else if (viewFlipper.getDisplayedChild() == 1) {
						viewFlipper.setDisplayedChild(2);
						bt3.setChecked(true);
						startPet.setVisibility(View.VISIBLE);
					} else if (viewFlipper.getDisplayedChild() == 2) {
						Toast.makeText(getApplicationContext(), "这已经是最后页！",
								Toast.LENGTH_SHORT).show();
					}
				} else if (x2 - x1 > 20) {// 向右滑 上一页
					if (viewFlipper.getDisplayedChild() == 0) {
						Toast.makeText(getApplicationContext(), "这已经是第一页！",
								Toast.LENGTH_SHORT).show();
					} else if (viewFlipper.getDisplayedChild() == 1) {
						viewFlipper.setDisplayedChild(0);
						startPet.setVisibility(View.GONE);
						bt1.setChecked(true);
					} else if (viewFlipper.getDisplayedChild() == 2) {
						viewFlipper.setDisplayedChild(1);
						bt2.setChecked(true);
						startPet.setVisibility(View.GONE);
					}
				}
			}
			return true;
		}
	};
	/**
	 * 圆点的点击事件
	 */
	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.guide_rd_bt1:
				viewFlipper.setDisplayedChild(0);
				startPet.setVisibility(View.GONE);
				break;
			case R.id.guide_rd_bt2:
				viewFlipper.setDisplayedChild(1);
				startPet.setVisibility(View.GONE);
				break;
			case R.id.guide_rd_bt3:
				viewFlipper.setDisplayedChild(2);
				startPet.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 简单控件的点击事件
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.guide_start_pet:
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			GuideActivity.this.finish();
			break;
		default:
			break;
		}
	}

}

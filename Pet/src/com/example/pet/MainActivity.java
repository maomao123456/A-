package com.example.pet;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences.Editor;
import com.example.pet.fragment.DongTaiFragment;
import com.example.pet.fragment.GonglueFragment;
import com.example.pet.fragment.MineFragment;
import com.example.pet.fragment.PetSquareFragment;
import com.example.pet.fragment.ResourceFragment;
import com.example.pet.fragmentadapter.MainFragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.pet.fragment.DongTaiFragment;
import com.example.pet.fragment.GonglueFragment;
import com.example.pet.fragment.MineFragment;
import com.example.pet.fragment.PetSquareFragment;
import com.example.pet.fragment.ResourceFragment;
import com.example.pet.fragmentadapter.MainFragmentAdapter;
/**
 * 登录后的页面,也是存放介个fragment的页面
 */
public class MainActivity extends FragmentActivity implements OnClickListener{
	ViewPager viewPager;
	TextView title1;
	TextView title2;
	TextView title3;
	View line1;
	View line2;
	RadioGroup radiogroup;
	RadioButton shouye;
	RadioButton pet;
	RadioButton mine;
	List<Fragment> fragmentList;
	MainFragmentAdapter fragmentAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		saveZhuangtai();
		initView();
		getData();
		fragmentAdapter=new MainFragmentAdapter(
				getSupportFragmentManager(), fragmentList);
		viewPager.setAdapter(fragmentAdapter);	
	}
	/**
	 * 保存APP初始状态
	 */
	public void saveZhuangtai(){
		Editor editor=getSharedPreferences("pet", MODE_PRIVATE).edit();
    	editor.putInt("kills", 0);
    	editor.commit();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Editor editor=getSharedPreferences("pet", MODE_PRIVATE).edit();
    	editor.putInt("kills", 1);
    	editor.commit();
		super.onDestroy();
	}
	/**
	 * 寻找id的方法
	 */
	public void initView(){
		viewPager=(ViewPager)findViewById(R.id.viewpager_main);
		title1=(TextView)findViewById(R.id.text_title1_main);
		title2=(TextView)findViewById(R.id.text_title2_main);
		title3=(TextView)findViewById(R.id.text_tilte3_main);
		line1=(View)findViewById(R.id.line1_main);
		line2=(View)findViewById(R.id.line2_main);
		radiogroup=(RadioGroup)findViewById(R.id.radio_group_main);
		shouye=(RadioButton)findViewById(R.id.radio_button_shouye_main);
		pet=(RadioButton)findViewById(R.id.radio_button_pet_main);
		mine=(RadioButton)findViewById(R.id.radio_button_mine_main);
		shouye.setOnClickListener(this);
		pet.setOnClickListener(this);
		mine.setOnClickListener(this);
		viewPager.setOnPageChangeListener(pageChangeListener);
		title1.setOnClickListener(this);
		title2.setOnClickListener(this);
	}
	/**
	 * title1和title2的手动切换效果
	 * 及底部按钮的监听事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.radio_button_shouye_main:
			viewPager.setCurrentItem(0);
			first();
			break;
		case R.id.radio_button_pet_main:
			viewPager.setCurrentItem(2);
			third();
			break;
		case R.id.radio_button_mine_main:
			viewPager.setCurrentItem(4);
			fifth();
			break;
		case R.id.text_title1_main:
			if(shouye.isChecked()){
				viewPager.setCurrentItem(0);
				first();
			}else if(pet.isChecked()){
				viewPager.setCurrentItem(2);
				third();
			}
			break;
		case R.id.text_title2_main:
			if(shouye.isChecked()){
				viewPager.setCurrentItem(1);
				second();
			}else if(pet.isChecked()){
				viewPager.setCurrentItem(3);
				fourth();
			}
			break;
		default:
			break;
		}
	}
	/**
	 * viewPager的滑动监听事件
	 */
	OnPageChangeListener pageChangeListener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			getItem();
		}
	};
	/**
	 * 根据滑动到哪一个页面了来改变底部导航栏的状态
	 */
	public void getItem(){
		switch(viewPager.getCurrentItem()){
		case 0:
			shouye.setChecked(true);
			first();
			break;
		case 1:
			shouye.setChecked(true);
			second();
			break;
		case 2:
			pet.setChecked(true);
			third();
			break;
		case 3:
			pet.setChecked(true);
			fourth();
			break;
		case 4:
			mine.setChecked(true);
			fifth();
			break;
		}
	}
	/**
	 * 第一个fragment页面的状态,下面的方法同理
	 */
	public void first(){
		title1.setText("资源");
		title2.setText("攻略");
		title1.setVisibility(View.VISIBLE);
		title2.setVisibility(View.VISIBLE);
		line1.setVisibility(View.VISIBLE);
		line2.setVisibility(View.GONE);
		title3.setVisibility(View.GONE);
	}
	public void second(){
		title1.setText("资源");
		title2.setText("攻略");
		title1.setVisibility(View.VISIBLE);
		title2.setVisibility(View.VISIBLE);
		line1.setVisibility(View.GONE);
		line2.setVisibility(View.VISIBLE);
		title3.setVisibility(View.GONE);
	}
	public void third(){
		title1.setText("广场");
		title2.setText("动态");
		title1.setVisibility(View.VISIBLE);
		title2.setVisibility(View.VISIBLE);
		line1.setVisibility(View.VISIBLE);
		line2.setVisibility(View.GONE);
		title3.setVisibility(View.GONE);
	}
	public void fourth(){
		title1.setText("广场");
		title2.setText("动态");
		title1.setVisibility(View.VISIBLE);
		title2.setVisibility(View.VISIBLE);
		line1.setVisibility(View.GONE);
		line2.setVisibility(View.VISIBLE);
		title3.setVisibility(View.GONE);
	}
	public void fifth(){
		title1.setVisibility(View.GONE);
		title2.setVisibility(View.GONE);
		title3.setVisibility(View.VISIBLE);
		title3.setText("我的");
		line1.setVisibility(View.GONE);
		line2.setVisibility(View.GONE);
	}
	/**
	 * 将fragment添加到list中以便于使用
	 */
	public void getData(){
		fragmentList = new ArrayList<Fragment>();
		ResourceFragment resourceFragment=new ResourceFragment();
		fragmentList.add(resourceFragment);
		GonglueFragment gonglueFragment=new GonglueFragment();
		fragmentList.add(gonglueFragment);
		PetSquareFragment petSquareFragment=new PetSquareFragment();
		fragmentList.add(petSquareFragment);
		DongTaiFragment dongTaiFragment=new DongTaiFragment();
		fragmentList.add(dongTaiFragment);
		MineFragment mineFragment=new MineFragment();
		fragmentList.add(mineFragment);
	}
	
}

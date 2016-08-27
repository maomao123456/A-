package com.example.pet.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.pet.R;
import com.example.pet.SquareDetailsActivity;
import com.example.pet.baseadapter.SquareAdapter;
import com.example.pet.lei.SquareGridview;
import com.example.pet.lei.SquareListview;

public class PetSquareFragment extends Fragment implements OnTouchListener{
	View view;
	View headview;
	View itemview;
	ListView listview;
	SquareListview square;
	SquareAdapter adapter;
	List<SquareListview> list;
	ViewFlipper viewflipper;
	GridView gridview;
	RadioGroup radiogroup;
	RadioButton radiobutton1,radiobutton2,radiobutton3,radiobutton4,radiobutton5;
	TextView pet_daren;
	ImageView pet2,pet3,pet4,img_next;
	int[] id={R.id.little_head_square,R.id.text_name_square,R.id.text_time_square
			,R.id.text_guanzhu_square,R.id.text_miaoshu_square,R.id.gridview_square
			,R.id.address_image_square,R.id.text_address_square,
			 R.id.text_collection_square,R.id.text_pinglun_square};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_square, null);
		headview=inflater.inflate(R.layout.fragment_square_head, null);
		itemview=inflater.inflate(R.layout.listview_item_square, null);
		initView();
		viewflipper.startFlipping();//开始轮播
		senMsg();
		getList();//获得下面listview的数据
		listview.addHeaderView(headview);
		adapter=new SquareAdapter(getActivity(), list, R.layout.listview_item_square, id);
		listview.setAdapter(adapter);//设置listview
		listview.setOnItemClickListener(itemClickListener);
		return view;
	}
	/**
	 * 找id
	 */
	public void initView(){
		listview=(ListView)view.findViewById(R.id.listview_square);
		gridview=(GridView)itemview.findViewById(R.id.gridview_square);
		viewflipper=(ViewFlipper)headview.findViewById(R.id.viewflipper_square);
		radiogroup=(RadioGroup)headview.findViewById(R.id.radiogroup_square);
		radiobutton1=(RadioButton)headview.findViewById(R.id.radiobutton1_square);
		radiobutton2=(RadioButton)headview.findViewById(R.id.radiobutton2_square);
		radiobutton3=(RadioButton)headview.findViewById(R.id.radiobutton3_square);
		radiobutton4=(RadioButton)headview.findViewById(R.id.radiobutton4_square);
		radiobutton5=(RadioButton)headview.findViewById(R.id.radiobutton5_square);
		pet_daren=(TextView)headview.findViewById(R.id.text_pet_talent_square);
		pet2=(ImageView)headview.findViewById(R.id.image_pet2_square);
		pet3=(ImageView)headview.findViewById(R.id.image_pet3_square);
		pet4=(ImageView)headview.findViewById(R.id.image_pet4_square);
		img_next=(ImageView)headview.findViewById(R.id.image_next_square);
		radiogroup.setOnCheckedChangeListener(checkedChangeListener);
		img_next.setOnClickListener(clickListener);
	}
	/**
	 * 图片轮播来改变底下小圆点的颜色
	 */
	public void buttonChange(){
		if(viewflipper.isFlipping()){
			switch(viewflipper.getDisplayedChild()){
			case 0:
				radiobutton1.setChecked(true);
				break;
			case 1:
				radiobutton2.setChecked(true);
				break;
			case 2:
				radiobutton3.setChecked(true);
				break;
			case 3:
				radiobutton4.setChecked(true);
				break;
			case 4:
				radiobutton5.setChecked(true);
				break;
			}
		}
	}
	/**
	 * 间隔时间发送消息进行更新广告
	 */
	public void senMsg() {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < i + 1; i++) {
					try {
						Thread.sleep(1);
						Message msg = new Message();
						handler.sendMessage(msg);
						if (i == 10) {
							i = 0;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			buttonChange();
		};
	};
	/**
	 * 通过改变小圆点来改变viewflipper的轮播图片
	 */
	OnCheckedChangeListener checkedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch(checkedId){
			case R.id.radiobutton1_square:
				viewflipper.stopFlipping();
				viewflipper.setDisplayedChild(0);
				viewflipper.startFlipping();
				break;
			case R.id.radiobutton2_square:
				viewflipper.stopFlipping();
				viewflipper.setDisplayedChild(1);
				viewflipper.startFlipping();
				break;
			case R.id.radiobutton3_square:
				viewflipper.stopFlipping();
				viewflipper.setDisplayedChild(2);
				viewflipper.startFlipping();
				break;
			case R.id.radiobutton4_square:
				viewflipper.stopFlipping();
				viewflipper.setDisplayedChild(3);
				viewflipper.startFlipping();
				break;
			case R.id.radiobutton5_square:
				viewflipper.stopFlipping();
				viewflipper.setDisplayedChild(4);
				viewflipper.startFlipping();
				break;
			}
		}
	};
	int[] imgs={R.drawable.pet_2,R.drawable.pet_3,R.drawable.pet_4,R.drawable.pet_5};
	int l=imgs.length;
	int numb=l;
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.image_next_square:
				int a=numb%l;
					pet2.setImageResource(imgs[a%l]);
					pet3.setImageResource(imgs[(a+1)%l]);
					pet4.setImageResource(imgs[(a+2)%l]);
					numb++;
				break;
			}
		}
	};
	/**
	 * listView的item项整体点击事件
	 */
	OnItemClickListener itemClickListener=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			System.out.println("21221222121102121012.422211220");
			Toast.makeText(getActivity(), "10104124412", Toast.LENGTH_SHORT).show();
			switch(position){
			case 1:
				Toast.makeText(getActivity(), "66666", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getActivity(),SquareDetailsActivity.class));
				break;
			}
		}
	};
	/**
	 * 添加listview中的内容的方法,未完待续
	 */
	public void getList(){
		list=new ArrayList<SquareListview>();
		List<SquareGridview> list_gridview=new ArrayList<SquareGridview>();
		SquareGridview  squareGridview=new SquareGridview();
		squareGridview.setImage((R.drawable.big_pet1_square));
		list_gridview.add(squareGridview);
		square=new SquareListview();
		square.setLittle_head(R.drawable.little_head1);
		square.setName("Wendy");
		square.setTime("30分钟前");
		square.setGuanzhu("+关注");
		square.setMiaoshu("世界那么大，我只想安静的睡个午觉。。。");
		square.setAddress("来自河北邯郸");
		square.setCollections(17);
		square.setPinglun(25);
		square.setList(list_gridview);
		list.add(square);
		
		SquareGridview  squareGridview1=new SquareGridview();
		list_gridview=new ArrayList<SquareGridview>();
		squareGridview1.setImage((R.drawable.big_pet2_square));
		list_gridview.add(squareGridview1);
		SquareGridview  squareGridview2=new SquareGridview();
		squareGridview2.setImage((R.drawable.big_pet3_square));
		list_gridview.add(squareGridview2);
		SquareGridview  squareGridview3=new SquareGridview();
		squareGridview3.setImage((R.drawable.big_pet2_square));
		list_gridview.add(squareGridview3);
		square=new SquareListview();
		square.setLittle_head(R.drawable.little_head2);
		square.setName("微笑AND");
		square.setTime("1小时前");
		square.setGuanzhu("+关注");
		square.setMiaoshu("堪称表情帝啊~~~");
		square.setAddress("来自北京海淀");
		square.setCollections(36);
		square.setPinglun(12);
		square.setList(list_gridview);
		list.add(square);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "66666", Toast.LENGTH_SHORT).show();
		return true;
	}
}

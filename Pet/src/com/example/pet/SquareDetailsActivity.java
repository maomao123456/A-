package com.example.pet;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.baseadapter.SquareDetailsListviewAdapter;
import com.example.pet.classes.SysApplication;
import com.example.pet.lei.SquareDetailsListview;

public class SquareDetailsActivity extends Activity {
	LayoutInflater inflater;
	View headview;
	ListView listview;
	GridView gridview1, gridview2;
	SquareDetailsListviewAdapter adapter;
	List<SquareDetailsListview> list;
	SquareDetailsListview details;
	RelativeLayout back;
	TextView fabu, guanzhu, sendComment;// 发送评论
	String str1;// 在本页设置的关注情况
	EditText commentEdit;
	LinearLayout commentLinear;// 评论的线性布局
	private String comment = "";		//记录对话框中的内容

	int[] id = { R.id.head_pinglun_xiangqing_square,
			R.id.name_pinglun_xiangqing_square, R.id.pinglun_xiangqing_square,
			R.id.time_pinglun_xiangqing_square };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangqing_square);
		SysApplication.getInstance().addActivity(this);
		init();
		listview.addHeaderView(headview);
		getList();
		adapter = new SquareDetailsListviewAdapter(this, list,
				R.layout.listview_item_xiangqing_square, id);
		listview.setAdapter(adapter);

	}

	/**
	 * 找id,并且给各个控件设置点击等事件
	 */
	@SuppressLint("InflateParams")
	public void init() {
		inflater = this.getLayoutInflater();
		headview = (View) inflater
				.inflate(R.layout.xiangqing_head_square, null);
		listview = (ListView) findViewById(R.id.listview_xiangqing_square);
		gridview1 = (GridView) headview
				.findViewById(R.id.gridview1_xiangqing_square);
		gridview2 = (GridView) headview
				.findViewById(R.id.gridview2_xiangqing_square);
		gridview1.setAdapter(new GridAdapter(this));
		gridview2.setAdapter(new GridAdapter2(this));
		back = (RelativeLayout) findViewById(R.id.back_xiangqing_square);
		back.setOnClickListener(onClickListener);
		fabu = (TextView) findViewById(R.id.fabu_square_details);
		fabu.setOnClickListener(onClickListener);
		guanzhu = (TextView) headview
				.findViewById(R.id.text_guanzhu_xiangqing_square);
		guanzhu.setOnClickListener(onClickListener);
		commentEdit = (EditText) findViewById(R.id.edittext_square_album);
		commentLinear = (LinearLayout) findViewById(R.id.comment_linear);
		sendComment = (TextView) findViewById(R.id.send_comment);
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_xiangqing_square:// 返回键的监听
				startActivity(new Intent(SquareDetailsActivity.this,
						MainActivity.class));
				SquareDetailsActivity.this.finish();
				break;
			case R.id.fabu_square_details:
				startActivity(new Intent(SquareDetailsActivity.this,
						PublishStatesActivity.class));
				break;
			case R.id.text_guanzhu_xiangqing_square:
				if (guanzhu.getText().toString().equals("+关注")) {
					guanzhu.setText("已关注");
				} else {
					AlertDialog alertDialog;
					Builder builder;
					builder = new AlertDialog.Builder(
							SquareDetailsActivity.this);
					builder.setMessage("你真的要取消对我的关注吗？");
					builder.setPositiveButton("是的", onClickListener2);
					builder.setNegativeButton("我再想想", onClickListener2);
					alertDialog = builder.create();
					alertDialog.show();
				}
				break;
			}
		}
	};
	/**
	 * 对是否取消关注的监听
	 */
	DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			switch (arg1) {
			case -1:
				guanzhu.setText("+关注");
				Toast.makeText(SquareDetailsActivity.this, "已取消关注",
						Toast.LENGTH_SHORT).show();
				break;
			case -2:
				break;
			}
		}
	};

	/**
	 * 判断对话框是否输入内容
	 */
	private boolean isEditEmply() {
		comment = commentEdit.getText().toString().trim();
		if(comment.equals("")){
			Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		commentEdit.setText("");
		return true;
	}

	/**
	 * 发表评论  为list中添加数据
	 */
	String str, str2, str3, str4;
	int img;
	int num;

	int[] heads = { R.drawable.head1_pinglun, R.drawable.head7_pinglun,
			R.drawable.head8_pinglun, R.drawable.head6_pinglun };
	String[] names = { "小小酥", "豆豆", "随遇而安", "喵了个咪" };
	String[] words = { "两个宝宝好萌啊", "酷酷哒!", "哈哈哈    好可爱啊", "~~~~可爱" };
	String[] times = { "1小时前", "30分钟前", "10分钟前", "5分钟前" };

	public void getList() {
		list = new ArrayList<SquareDetailsListview>();
		for (int i = 0; i < names.length; i++) {
			details = new SquareDetailsListview();
			details.setHead(heads[i]);
			details.setName(names[i]);
			details.setWords(words[i]);
			details.setTime(times[i]);
			list.add(details);
		}
		if (num == 1) {

		}
		details = new SquareDetailsListview();
		details.setHead(img);// 头像
		details.setName(str2);// 昵称
		details.setWords(str3);// 评论内容
		details.setTime(str4);// 评论时间
		list.add(details);

	}
	
	/**
	 * 发表评论的事件点击监听
	 */
	private final class ClickListener implements OnClickListener{
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.send_comment://发表评论按钮
				if(isEditEmply()){
					
				}
				break;

			default:
				break;
			}
		}
		
	}

	/**
	 * 设置gridView的步骤
	 */
	int[] imgs = { R.drawable.big_pet4_square };
	int[] imgs2 = { R.drawable.head2_pinglun, R.drawable.head3_pinglun,
			R.drawable.head4_pinglun, R.drawable.head5_pinglun,
			R.drawable.head6_pinglun };

	private class GridAdapter extends BaseAdapter {

		Activity context;

		public GridAdapter(Activity context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = context.getLayoutInflater().inflate(
						R.layout.gridview_item_xiangqing_square, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.img_tupian_xiangqing_square);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setImageResource(imgs[position]);
			return convertView;
		}

		class ViewHolder {
			ImageView image;
		}
	}

	private class GridAdapter2 extends BaseAdapter {

		Activity context;

		public GridAdapter2(Activity context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs2.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = context.getLayoutInflater().inflate(
						R.layout.gridview_item_xiangqing_square, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.img_tupian_xiangqing_square);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setImageResource(imgs2[position]);
			return convertView;
		}

		class ViewHolder {
			ImageView image;
		}
	}
}

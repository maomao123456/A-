package com.example.pet;



import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PublishPetActivity extends Activity{
	RelativeLayout back;
	TextView fabu;
	ImageView image;
	EditText name,type,describe;
	View popview;
	LayoutInflater inflater;
	PopupWindow popWindow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_pet);
		init();
	}
	public void init(){
		inflater=this.getLayoutInflater();
		back=(RelativeLayout)findViewById(R.id.back_publish_petdetails_square);
		fabu=(TextView)findViewById(R.id.publish_publish_pet);
		image=(ImageView)findViewById(R.id.pet_image_publish);
		name=(EditText)findViewById(R.id.editname_publish);
		type=(EditText)findViewById(R.id.edittype_publish);
		describe=(EditText)findViewById(R.id.editdescribe_publish);
		back.setOnClickListener(clickListener);
		fabu.setOnClickListener(clickListener);
		image.setOnClickListener(clickListener);
		popview=(View)inflater.inflate(R.layout.popupwindow_add_head, null);
	}
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.back_publish_petdetails_square:
				startActivity(new Intent(PublishPetActivity.this,MainActivity.class));
				PublishPetActivity.this.finish();
				break;
			case R.id.publish_publish_pet:
				startActivity(new Intent(PublishPetActivity.this,PublishPetActivity.class));
				toast("发布成功!");
				break;
			case R.id.pet_image_publish:
				creatPopupWindow();
				break;
			case R.id.popup_window_bg:
				popWindow.dismiss();
				break;
			case R.id.album_head_pop:
				Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
			    intent.setType("image/*");
			    intent.putExtra("crop", "true");
			    intent.putExtra("aspectX", 1);
			    intent.putExtra("aspectY", 1);
			    intent.putExtra("outputX", 80);
			    intent.putExtra("outputY", 80);
			    intent.putExtra("return-data", true);

			    startActivityForResult(intent, 0);
				popWindow.dismiss();
				break;
			case R.id.camera_head_pop:
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 startActivityForResult(camera, 1);
				break;
			default:
				break;
			}
		}
	};
	public void creatPopupWindow(){
		popWindow=new PopupWindow(popview, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.setTouchable(true);
		popWindow.setOutsideTouchable(true);
		popWindow.update();
		popWindow.setFocusable(true);
		popWindow.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popWindow.showAtLocation(popview,Gravity.BOTTOM,0,0);
		TextView camera=(TextView)popview.findViewById(R.id.camera_head_pop);
		TextView album=(TextView)popview.findViewById(R.id.album_head_pop);
		TextView cancel=(TextView)popview.findViewById(R.id.cancel_head_pop);
		View popbg=(View)popview.findViewById(R.id.popup_window_bg);
		camera.setOnClickListener(clickListener);
		album.setOnClickListener(clickListener);
		cancel.setOnClickListener(clickListener);
		popbg.setOnClickListener(clickListener);
	}
	/**
	 * toast方法
	 * @param string
	 */
	public void toast(String string) {
		Toast toast=Toast.makeText(PublishPetActivity.this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	/**
	 * 相册相机的方法
	 */
	
}

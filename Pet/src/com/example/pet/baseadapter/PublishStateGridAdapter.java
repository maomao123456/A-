/*package com.example.pet.baseadapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.pet.R;

public class PublishStateGridAdapter extends BaseAdapter{
	private LayoutInflater inflater;  
	 private int selectedPosition = -1;  
     private boolean shape;  
     List<HashMap<String, Object>> list;

     public boolean isShape() {  
         return shape;  
     }  

     public void setShape(boolean shape) {  
         this.shape = shape;  
     }  

     public PublishStateGridAdapter(Context context) {  
         inflater = LayoutInflater.from(context);  
     }  

     public void update() {  
         loading();  
     }  

     public int getCount() {  
         if(list.size() == 9){  
             return 9;  
         }  
         return (list.size() + 1);  
     }  

     public Object getItem(int arg0) {  
         return null;  
     }  

     public long getItemId(int arg0) {  
         return 0;  
     }  

     public void setSelectedPosition(int position) {  
         selectedPosition = position;  
     }  

     public int getSelectedPosition() {  
         return selectedPosition;  
     }  

     public View getView(int position, View convertView, ViewGroup parent) {  
         ViewHolder holder = null;  
         if (convertView == null) {  
             convertView = inflater.inflate(R.layout.,  
                     parent, false);  
             holder = new ViewHolder();  
             holder.image = (ImageView) convertView  
                     .findViewById(R.id.item_grida_image);  
             convertView.setTag(holder);  
         } else {  
             holder = (ViewHolder) convertView.getTag();  
         }  

         if (position ==Bimp.tempSelectBitmap.size()) {  
             holder.image.setImageBitmap(BitmapFactory.decodeResource(  
                     getResources(), R.drawable.icon_addpic_unfocused));  
             if (position == 9) {  
                 holder.image.setVisibility(View.GONE);  
             }  
         } else {  
             holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());  
         }  

         return convertView;  
     } 
     public class ViewHolder {  
         public ImageView image;  
     }  

     Handler handler = new Handler() {  
         public void handleMessage(Message msg) {  
             switch (msg.what) {  
             case 1:  
                 adapter.notifyDataSetChanged();  
                 break;  
             }  
             super.handleMessage(msg);  
         }  
     };  

     public void loading() {  
         new Thread(new Runnable() {  
             public void run() {  
                 while (true) {  
                     if (Bimp.max == Bimp.tempSelectBitmap.size()) {  
                         Message message = new Message();  
                         message.what = 1;  
                         handler.sendMessage(message);  
                         break;  
                     } else {  
                         Bimp.max += 1;  
                         Message message = new Message();  
                         message.what = 1;  
                         handler.sendMessage(message);  
                     }  
                 }  
             }  
         }).start();  
     }  
}
*/
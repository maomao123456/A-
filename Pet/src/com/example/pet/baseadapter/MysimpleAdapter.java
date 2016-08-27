package com.example.pet.baseadapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.XianqingActivity;

public class MysimpleAdapter extends SimpleAdapter {
	ListView listView;
	ImageView imageView;
	View view;
	LayoutInflater layoutInflater;
	Context context;

	public MysimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		layoutInflater=LayoutInflater.from(context);
		view=(View)layoutInflater.inflate(R.layout.activity_simpleadapter_gonglue, null);
		
		// TODO Auto-generated constructor stub
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = super.getView(position, convertView, parent);
        imageView=(ImageView) v.findViewById(R.id.imageviewJt);
        imageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				switch (position) {
				case 1:
					numb=position;
					tiao();
					break;
				case 2:
					numb=position;
					tiao();
					break;
				case 3:
					numb=position;
					tiao();
					break;

				default:
					break;
				}
			}
		});
                
      return v;
	};
	int numb=0;
	 public void tiao(){
		 Intent intent=new Intent(context, XianqingActivity.class);
		 intent.putExtra("position", numb);
		 context.startActivity(intent);
     }

}


package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class PetDetailsActivity extends Activity{
	RelativeLayout back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_details);
		init();
	}
	public void init(){
		back=(RelativeLayout)findViewById(R.id.back_petdetails_square);
		back.setOnClickListener(clickListener);
	}
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.back_petdetails_square:
				startActivity(new Intent(PetDetailsActivity.this, MainActivity.class));
				PetDetailsActivity.this.finish();
				break;
			}
		}
	};
}

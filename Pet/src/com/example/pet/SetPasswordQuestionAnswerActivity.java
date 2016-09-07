package com.example.pet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;

public class SetPasswordQuestionAnswerActivity extends Activity {

	TextView forQuestionOne, forQuestionTwo, forQuestionThree;
	EditText questionOneAnswer, questionTwoAnswer, questionThreeAnswer;
	Button saveAnswer, backQuestion;
	
	/**
	 * 获得用户 登录后的id 获得当前用户id的方法
	 */
	String id;
	public void getId() {
		SharedPreferences pf = getSharedPreferences("pet_user", MODE_PRIVATE);
		id = pf.getString("id", "");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_set_password_protect_answer);
		initView();
	}
	
	private void initView(){
		forQuestionOne = (TextView) findViewById(R.id.for_question_one);
		forQuestionTwo = (TextView) findViewById(R.id.for_question_two);
		forQuestionThree = (TextView) findViewById(R.id.for_question_three);
		questionOneAnswer = (EditText) findViewById(R.id.question_one_answer);
		questionTwoAnswer = (EditText) findViewById(R.id.question_two_answer);
		questionThreeAnswer = (EditText) findViewById(R.id.question_three_answer);
		saveAnswer = (Button) findViewById(R.id.save_question_answer);
		SharedPreferences sp = getSharedPreferences(id, Context.MODE_PRIVATE);
		backQuestion = (Button) findViewById(R.id.back_reset_security_set);
		forQuestionOne.setText(sp.getString("q1", ""));
		forQuestionTwo.setText(sp.getString("q2", ""));
		forQuestionThree.setText(sp.getString("q3", ""));
		//触发点击
		saveAnswer.setOnClickListener(clickListener);
	}
	
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_reset_security_set:
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ResetSecuritySetActivity.class);
				startActivity(intent);
				SetPasswordQuestionAnswerActivity.this.finish();
				break;
			case R.id.save_question_answer:
				cacheAnswer();
				break;

			default:
				break;
			}
		}
	};
	
	private void cacheAnswer(){
		SharedPreferences preferences = getSharedPreferences(id, Context.MODE_PRIVATE);
		String answer1 = questionOneAnswer.getText().toString();
		String answer2 = questionTwoAnswer.getText().toString();
		String answer3 = questionThreeAnswer.getText().toString();
		Editor edit = preferences.edit();
		edit.putString("answer1", answer1);
		edit.putString("answer2", answer2);
		edit.putString("answer3", answer3);
		edit.commit();
		Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
	}
}

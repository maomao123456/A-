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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;

public class ResetSecuritySetActivity extends Activity {

	ImageButton backAccountManager;
	Button saveSecuritySet;
	EditText questionOne, questionTwo, questionThree;
	TextView promptQuestion;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_security_set);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	// 初始化视图
	private void initView() {
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveSecuritySet = (Button) findViewById(R.id.save_security_set);
		questionOne = (EditText) findViewById(R.id.question_one);
		questionTwo = (EditText) findViewById(R.id.question_two);
		questionThree = (EditText) findViewById(R.id.question_three);
		promptQuestion = (TextView) findViewById(R.id.prompt_question);
		SharedPreferences preference = getSharedPreferences("question", Context.MODE_PRIVATE);
		questionOne.setText(preference.getString("q1", ""));
		questionTwo.setText(preference.getString("q2", ""));
		questionThree.setText(preference.getString("q3", ""));
		// 触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveSecuritySet.setOnClickListener(clickListener);
	}

	// 按钮点击方法
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_account_manager:
				backAccountManager();
				break;
			case R.id.save_security_set:
				if(!checkEdit()){
					return;
				}
				cacheQuestion();
				toAnswer();
				break;

			default:
				break;
			}
		}
	};

	// 返回管理设置界面
	private void backAccountManager() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), AccountManagerActivity.class);
		startActivity(intent);
		ResetSecuritySetActivity.this.finish();
	}
	
	// 问题输入框
	private boolean checkEdit(){
		String qOne = questionOne.getText().toString();
		String qTwo = questionTwo.getText().toString();
		String qThree = questionThree.getText().toString();
		if(qOne.isEmpty() && qTwo.isEmpty() && qThree.isEmpty()){
			promptQuestion.setText("至少输入一个问题");
			return false;
		} else if(qOne.length() > 20 || qTwo.length() > 20 || qThree.length() > 20){
			promptQuestion.setText("问题字数不超过20个字");
			return false;
		} else {
			promptQuestion.setText("");
			return true;
		}			
	}
	
	/**
	 * 问题缓存
	 */
	private void cacheQuestion(){
		String q1 = questionOne.getText().toString();
		String q2 = questionTwo.getText().toString();
		String q3 = questionThree.getText().toString();
		SharedPreferences sp = getSharedPreferences("question", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("q1", q1);
		editor.putString("q2", q2);
		editor.putString("q3", q3);
		editor.commit();
		Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
	}
	
	//跳转到设置问题答案的页面
	private void toAnswer(){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), SetPasswordQuestionAnswerActivity.class);
		startActivity(intent);
	}

}

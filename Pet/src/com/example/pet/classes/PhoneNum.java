package com.example.pet.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNum {

	//判断输入的电话号码是否正确
	public static boolean isRightPhoneNumber(String phoneNumber){
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String regExp = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
}

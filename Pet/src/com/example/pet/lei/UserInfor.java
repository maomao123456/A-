package com.example.pet.lei;

import java.util.Date;

public class UserInfor {
	String nickname;
	String gender;
	Date birthday;
	String star;
	String occupation;
	String company;
	String address;
	String hometown;
	String email;
	String remark;
	
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public String getNickname(){
		return this.nickname;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	public String getGender(){
		return this.gender;
	}
	
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	public Date getBirthday(){
		return this.birthday;
	}
	
	public void setStar(String star){
		this.star = star;
	}
	public String getStar(){
		return this.star;
	}
	
	public void setOccupation(String occupation){
		this.occupation = occupation;
	}
	public String getOccupation(){
		return this.occupation;
	}
	
	public void setCompany(String company){
		this.company = company;
	}
	public String getCompany(){
		return this.company;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	
	public void setHometown(String hometown){
		this.hometown = hometown;
	}
	public String getHometown(){
		return hometown;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
}

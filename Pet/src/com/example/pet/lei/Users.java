package com.example.pet.lei;

/**
 * 网络获取的的用户信息类
 * @author Administrator
 *
 */
public class Users {
	/**
	 * 网络获取用户的ID
	 */
	private String id;//用户ID  
	/**
	 * 网络获取用户的昵称
	 */
    private String niCheng;//用户昵称  
    /**
     * 网络获取用户所在地
     */
    private String city;
    /**
     * 网络获取用户的头像(头像为url需用Bitmap转换 )
     */
    private String touxiang;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNiCheng() {
		return niCheng;
	}
	public void setNiCheng(String niCheng) {
		this.niCheng = niCheng;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTouxiang() {
		return touxiang;
	}
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
    
    
}

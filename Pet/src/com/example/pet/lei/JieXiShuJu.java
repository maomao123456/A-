package com.example.pet.lei;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 解析数据类 接口
 *返回JSON
 */
public class JieXiShuJu{
	/**
     * GET请求 返回Json.toString
     * @param path 网页路径
     * @param params 参数
     * @param values 参数的值
     * @return JSON
     */
    public static String doGet(String path,String[] params,String[] values){
        try {
            HttpClient httpClient = new DefaultHttpClient();//创建httpClient对象
            String url =JieXiShuJu.praiseGetParams(path, params, values);//组合网页参数
            HttpGet httpGet = new HttpGet(url);//创建GET对象
            HttpResponse httpResponse = httpClient.execute(httpGet);//开始访问,用HttpResponse来收返回的数据
            //如果连接成功，读取数据
            System.out.println("等待连接。。。。。。。《》》》》》");
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            	 System.out.println("连接成功！《》》》》》");
                return JieXiShuJu.readData(httpResponse.getEntity().getContent());//通过HttpResponse来获得对应的输出流
            }else{
            	 System.out.println("连接失败！《》》》》》");
            	return null;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("连接成功！返回空《135465《》》》》》");
        return null;
    }
    /**
     * 组合URL 返回url
     * @param path 路径
     * @param params 参数
     * @param values 值
     * @return URL
     */
    public static String praiseGetParams(String path,String[] params,String[] values){
        String url = "";
        //如果params和values为空就返回path
        if(params==null||values==null){
            url = path;
            System.out.println("//如果params和values为空就返回path");
        }
        //如果参数和值的大小不同，抛出异常
        else if(params.length != values.length){
            try {
                throw new Exception("参数异常");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("参数异常！！！！《》》《《《》");
        }
        //正常解析情况
        else{
            url = path + "?";
            for(int i=0;i<params.length;i++){
                url += (params[i] + "=" + values[i] + "&");
            }
            //截取最后的一个字符"&"
            url = url.substring(0, url.length()-1);
        }
        System.out.println("完整路径---------"+url);
        return url;
    }
    /**
     * 通过字节输出流读取数据
     * @param is
     * @return JSON
     */
    public static String readData(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));//将字节输出流转换为字符输出流
        StringBuffer sb = new StringBuffer();
        String line = "";
        try {
            line = br.readLine();
            //循环读取数据
            while(line != null){
                sb.append(line + "\n");
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}

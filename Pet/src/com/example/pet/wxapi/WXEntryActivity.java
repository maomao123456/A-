package com.example.pet.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//注册API
        IWXAPI api;
    	api = WXAPIFactory.createWXAPI(this, "APP_ID");
        api.handleIntent(getIntent(), this);
    }
	@Override
	public void onResp(BaseResp resp) {
		if(resp instanceof SendAuth.Resp){
			SendAuth.Resp newResp = (SendAuth.Resp) resp;
			//获取微信传回的code
			String token = newResp.token;
		}
	}
	@Override
	public void onReq(BaseReq arg0) {
		
	}
	
}

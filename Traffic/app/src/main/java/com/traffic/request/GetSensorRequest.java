package com.traffic.request;

import com.traffic.bean.SensorBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yuan on 2017/2/9.
 */

public class GetSensorRequest extends BaseRequest {
    private SensorBean mSensorBean;
    private boolean isSuccess=false;
    public GetSensorRequest(SensorBean mSensorBean) {
        this.mSensorBean=mSensorBean;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    protected String getActionName() {
        return "GetAllSense.do";
    }

    @Override
    protected String onGetJasonBody() {
        return "";
    }

    @Override
    protected void onJasonParese(String responseStr) {
        try {
            JSONObject jsonObject=new JSONObject(responseStr);
            if(jsonObject.has("pm2.5")){
                mSensorBean.setPm25(jsonObject.getInt("pm2.5"));
            }
            if(jsonObject.has("co2")){
                mSensorBean.setCO2(jsonObject.getInt("co2"));
            }
            if(jsonObject.has("temp")){
                mSensorBean.setTemperature(jsonObject.getInt("temp"));
            }
            if(jsonObject.has("LightIntensity")){
                mSensorBean.setLight(jsonObject.getInt("LightIntensity"));
            }
            if(jsonObject.has("humidity")){
                mSensorBean.setHumidity(jsonObject.getInt("humidity"));
            }
            isSuccess=true;
        } catch (JSONException e) {
            e.printStackTrace();
            isSuccess=false;
        }
        super.onJasonParese(responseStr);
    }
}

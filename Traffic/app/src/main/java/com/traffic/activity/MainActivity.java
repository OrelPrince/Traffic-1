package com.traffic.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.traffic.ClientApp;
import com.traffic.bean.SensorBean;
import com.traffic.request.BaseRequest;
import com.traffic.request.GetSensorRequest;
import com.yuan.traffic.R;

/**
 * Created by yuan on 2017/1/16.
 */

public class MainActivity extends AppCompatActivity {
    private ClientApp mApp;
    private GetSensorRequest mSensorRequest;
    private SensorBean mSensorBean;
    private VideoView mVideoView;
    private TextView mValueTV,mStutusTV;
    private Button mSetIPBtn,mSetValueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startThread();
        upDataView();
    }
    //启动线程
    private void startThread() {

    }


    private void initView() {

        mVideoView=(VideoView)findViewById(R.id.video_video);
        mValueTV=(TextView) findViewById(R.id.value_text);
        mStutusTV=(TextView) findViewById(R.id.status_text);
        mSetIPBtn=(Button) findViewById(R.id.set_ip_btn);
        mSetValueBtn=(Button) findViewById(R.id.set_value_btn);
        mApp=new ClientApp();
        mSensorBean=new SensorBean();
        mSensorRequest=new GetSensorRequest(mSensorBean);
        mSetIPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showSetIpDialog();
            }
        });
        mSensorRequest.setOnResponseEventListener(new BaseRequest.OnResponseEventListener() {
            @Override
            public void onResponse(BaseRequest request, BaseRequest.RequestResult result) {

            }
        });
        mSetValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowValueDialog();
            }
        });
        showSetIpDialog();
    }

    private void setShowValueDialog() {
        final Dialog myDialog = new Dialog(this);//创建一个对话框
        myDialog.show();
        //设置对话框的界面布局
        Window win = myDialog.getWindow();

        win.setContentView(R.layout.set_value_dialog);
        win.setBackgroundDrawable(new ColorDrawable(0));
        //设置关闭按钮
        win.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

    }
    //更新当前数据
    @SuppressLint("StringFormatMatches")
    private void upDataView() {

    }
    //设置IP Dialog
    private void showSetIpDialog()
    {
        final Dialog myDialog = new Dialog(this);//创建一个对话框
        myDialog.show();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));  //设置背景颜色
        //设置对话框的界面布局
        Window win = myDialog.getWindow();
        win.setContentView(R.layout.set_ip_dialog);
        //设置关闭按钮
        win.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        final EditText ip1ET = (EditText)win.findViewById(R.id.ip1_edit_text);
        final EditText ip2ET = (EditText)win.findViewById(R.id.ip2_edit_text);
        final EditText ip3ET = (EditText)win.findViewById(R.id.ip3_edit_text);
        final EditText ip4ET = (EditText)win.findViewById(R.id.ip4_edit_text);

        win.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击OK以后，设置IP地址
                try
                {
                    int ip1,ip2,ip3,ip4;
                    ip1 = Integer.parseInt(ip1ET.getText().toString().trim());
                    ip2 = Integer.parseInt(ip2ET.getText().toString().trim());
                    ip3 = Integer.parseInt(ip3ET.getText().toString().trim());
                    ip4 = Integer.parseInt(ip4ET.getText().toString().trim());
                    String ipStr =ip1+"."+ip2+"."+ip3+"."+ip4;
                    //保存新的IP地址
                    mApp.setServerIpStr(ipStr);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,"设置IP地址有误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package com.traffic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.traffic.adpater.GuideAdapter;
import com.traffic.bean.GuideBean;
import com.yuan.traffic.R;

import java.util.ArrayList;

/**
 * Created by yuan on 2017/1/16.
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private ArrayList<GuideBean> mList;
    private GuideAdapter mAdapter;
    private LinearLayout mTipsGroupView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_main);
        initView();
        upDataView();
    }


    //初始化控件
    private void initView() {
        mViewPager=(ViewPager) findViewById(R.id.guide_main_vp);
        mTipsGroupView = (LinearLayout)findViewById(R.id.viewGroup);
        mList=new ArrayList<>();
        mList.add(new GuideBean(R.drawable.guide_1));
        mList.add(new GuideBean(R.drawable.guide_2));
        setupTipsImage();
    }
    //更新UI
    private void upDataView() {

        mAdapter=new GuideAdapter(this,mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTipImageSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //构建图片位置标记点
    private void setupTipsImage()
    {
        mTipsGroupView.removeAllViews();
        for(int i=0; i<mList.size(); i++)
        {
            ImageView imageView = new ImageView(this);
            if(i == 0){
                imageView.setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                imageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            imageView.setLayoutParams(new LinearLayout.LayoutParams(5,5));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(24, 24));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            mTipsGroupView.addView(imageView, layoutParams);
        }
    }

    //根据当前的图片位置来高亮相应的标记点
    private void setTipImageSelect(int selectItems){
        for(int i=0; i<mTipsGroupView.getChildCount(); i++)
        {
            View childView = mTipsGroupView.getChildAt(i);
            if(i == selectItems){
                childView.setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                childView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
}

package com.example.qinggong.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.adsmogo.adview.AdsMogoLayout;
import com.example.qinggong.R;

import com.example.qinggong.util.Banner;
import com.example.qinggong.util.CustomAlertDialog;
import com.example.qinggong.util.CustomAlertDialogCallbackListener;
import com.example.qinggong.util.HideAdUtil;
import com.example.qinggong.util.Interstitial;

/**
 * Created by ZJGJK03 on 2014/12/3.
 */
public class MainActivity extends BaseActivity {

    Button btn_done, btn_ready,btn_detail,btn_show;

    Banner banner;
    Interstitial interstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //广告开始
        banner=new Banner(MainActivity.this);
        banner.initBanner();
        interstitial=new Interstitial(MainActivity.this);
        interstitial.initInterstitial();
        //广告结束

        btn_done = (Button) findViewById(R.id.main_btn_done);
        btn_ready = (Button) findViewById(R.id.main_btn_ready);
        btn_detail=(Button)findViewById(R.id.main_btn_detail);

        if (btn_done != null)
            btn_done.setOnClickListener(listener);
        if (btn_ready != null)
            btn_ready.setOnClickListener(listener);
        if(btn_detail!=null)
            btn_detail.setOnClickListener(listener);
        btn_show=(Button)findViewById(R.id.main_btn_ShowAd);
        if(HideAdUtil.isHideAd())
        {
            btn_show.setVisibility(View.GONE);
        }
        if(btn_show!=null)
            btn_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interstitial.showInterstitial();
                }
            });
    }


    @Override
    protected void onDestroy() {
        //清理Banner广告
        banner.clearThread();
        banner.clear();
        interstitial.removeInterstitial();
        super.onDestroy();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.main_btn_done:
                    intent = new Intent(MainActivity.this, DoneActivity.class);
                    startActivity(intent);
                    break;
                case R.id.main_btn_ready:
                    intent = new Intent(MainActivity.this, ReadyActivity.class);
                    startActivity(intent);
                    break;
                case R.id.main_btn_detail:
                    intent=new Intent(MainActivity.this,DetailActivity.class);
                    startActivity(intent);
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        interstitial.changeCurrentActivity();
    }

    void exitApp() {
        CustomAlertDialog alertDialog=new CustomAlertDialog();
        alertDialog.setCancel(true,getResources().getString(R.string.exit_btn_cancel_text));
        alertDialog.setOk(true,getResources().getString(R.string.exit_btn_ok_text));
        alertDialog.setContent(true,getResources().getString(R.string.exit_tv_content_text));
        alertDialog.setTitleText(getResources().getString(R.string.exit_tv_title_text));
        alertDialog.setCancelable(true);
        alertDialog.showDialog(MainActivity.this,new CustomAlertDialogCallbackListener() {
            @Override
            public void onClickOk() {
                ActivityCollector.finishAll();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }
}

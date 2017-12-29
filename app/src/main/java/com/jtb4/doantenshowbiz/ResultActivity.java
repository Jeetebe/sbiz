package com.jtb4.doantenshowbiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

import utils.Const;
import utils.Faceb;
import utils.Osin;

public class ResultActivity extends AppCompatActivity {

    private ImageButton bnt_checked,btn_share,btn_play;
    private TextView txtscore,txtbest;
    private Faceb faceb;
    private Osin osin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        osin=new Osin(this);
        faceb=new Faceb(this);
        faceb.initfacebook(savedInstanceState);

        Intent intent = getIntent();
        int best= intent.getIntExtra("best",0);
        int score= intent.getIntExtra("score",0);

        bnt_checked=(ImageButton) findViewById(R.id.btn_check);
        btn_share=(ImageButton) findViewById(R.id.btn_share);
        btn_play=(ImageButton) findViewById(R.id.btn_playgame);

        txtscore=(TextView) findViewById(R.id.txtscore);
        txtbest=(TextView) findViewById(R.id.txtbest);

        txtbest.setText("BEST:"+Integer.toString(best));
        txtscore.setText("SCORE:"+Integer.toString(score));

        bnt_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent data = new Intent();
                String text = "Result bnt_checked";
                data.setData(Uri.parse(text));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                faceb.onClickPostStatusUpdate();
                osin.log2server(Const.share_app_fb+";device:");
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent data = new Intent();
                String text = "Result btn_play";
                data.setData(Uri.parse(text));
                setResult(RESULT_OK, data);
                finish();
            }
        });

        //NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        //adView.loadAd(new AdRequest.Builder().build());



    }

}

package com.jtb4.doantenshowbiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import utils.Const;
import utils.Faceb;
import utils.Osin;
import utils.Util;

public class MainUIActivity extends AppCompatActivity {


    private Button btnbatdau, btntieusu, btnnhanxu;
    private ImageView btnstore, btnfb;
    int solan=0;

    private Faceb faceb;
    private Osin osin;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

        osin=new Osin (this);
        faceb=new Faceb(this);
        faceb.initfacebook(savedInstanceState);

        btnbatdau = (Button) findViewById(R.id.btnbatdau);
        btntieusu = (Button) findViewById(R.id.btntieusu);
        btnnhanxu = (Button) findViewById(R.id.btnnhanxu);

        btnstore = (ImageView) findViewById(R.id.btnstore);
        btnfb = (ImageView) findViewById(R.id.btnfb);


        settings =getSharedPreferences(Const.PREFS_NAME, 0);
        solan=settings.getInt("solan", 0);
        Boolean firstrandom=settings.getBoolean("firstrandom",true);
        if (firstrandom)
        {
            String randomstring= Util.get_randomString();
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("randomstring", randomstring);
            editor.putBoolean("firstrandom",false);
            editor.commit();

        }

        btnbatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainUIActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btntieusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainUIActivity.this,
                        PicActivity.class);
                startActivity(i);

            }
        });
        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                osin.GoPLay();
            }
        });
        btnfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                faceb.onClickPostStatusUpdate();
                osin.log2server(Const.share_app_fb+";device:");
            }
        });

    }
}

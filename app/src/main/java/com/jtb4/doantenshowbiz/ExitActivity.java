package com.jtb4.doantenshowbiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

public class ExitActivity extends AppCompatActivity {
    private TextView txtthoat, txthuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        txtthoat=(TextView) findViewById(R.id.txtthoat);
        txthuy=(TextView) findViewById(R.id.txthuy);

        txtthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent data = new Intent();
                String text = "Result btn_thoat";
                data.setData(Uri.parse(text));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        txthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());

    }

}

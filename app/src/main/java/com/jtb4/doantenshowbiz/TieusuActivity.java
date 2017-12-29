package com.jtb4.doantenshowbiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import object.Nghesi;
import utils.Osin;
import utils.Util;

public class TieusuActivity extends AppCompatActivity {
    private WebView webview;
    private Nghesi nghesi;
    private Util util;
    private String TAG="MyActivity";

    private ImageView imgnghesi;
    private ImageButton bntclose;
    private TextView txtname;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tieusu);
        util=new Util(this);

        imgnghesi =(ImageView) findViewById(R.id.imageNghesi);
        bntclose=(ImageButton) findViewById(R.id.btn_close);
        txtname=(TextView) findViewById(R.id.txtname);

        Intent intent = getIntent();
        nghesi=(Nghesi) intent.getExtras().getSerializable("nghesi");
        //Nghesi nghesi=new Nghesi("bichphuong","tieu-su-ca-si-bich-phuong-370301","","","");

        util.logi("nghe si:"+nghesi.getname());
        webview=(WebView) findViewById(R.id.webview);
        //webview.loadUrl("http://nguoinoitieng.vn/tieu-su/tieu-su-son-tung-m-tp-370270");
        String html=buildtml(get_tieusufromweb(nghesi.getnameid()));
        //Log.i(TAG,"html:"+html);
        webview.loadData(html, "text/html; charset=utf-8", "utf-8");
        AQuery aq=new AQuery(this);
        aq.id(imgnghesi).image(nghesi.geturlimg(), true, true, 0, R.drawable.ic_launcher, null, AQuery.FADE_IN);
        txtname.setText(nghesi.getname());

        bntclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Load_admob_full();
                finish();
            }
        });


        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
    }




    private String buildtml(String kqua){
        String customHtml = "<!DOCTYPE html><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><meta name=\"viewport\" content=\"width=device-width\"><title>Untitled 1</title></head><body>" ;
        customHtml=customHtml+kqua	+"</body></html>";
        return customHtml;
    }

    private String get_tieusufromweb(String nameid) {
        String kqua="";
        Display display = getWindowManager().getDefaultDisplay();
        int width=display.getWidth();
        String url = "http://nguoinoitieng.vn/tieu-su/" + nameid;
        Util.logi("url:"+url);
        Document doc;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            doc = Jsoup.connect(url).timeout(0).get();
            //outLog(url);
            Elements links = doc.select("p");
            //Elements links = doc.getElementsByClass("descnnt rowcontentnnt mainitem");
            for (Element link : links) {
                String node=link.html();
               kqua=kqua+link;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return kqua;
    }
    @Override
    public void onBackPressed() {
        Load_admob_full();
        finish();
    }

    private void Load_admob_full(){

		    	mInterstitialAd = new InterstitialAd(this);
		        mInterstitialAd.setAdUnitId("ca-app-pub-8623108209004118/4456612788");
		        mInterstitialAd.setAdListener(new AdListener() {
		            @Override
		            public void onAdClosed() {
		                //requestNewInterstitial();
		            	//finish();
		            }
		            @Override
		            public void onAdLoaded() {
		                    if (mInterstitialAd.isLoaded()) {
		                    	mInterstitialAd.show();
		                    }
		            }
		        });
		        requestNewInterstitial();

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("DEE5DD83A2C2CE7B76777723DEE86B0B")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}

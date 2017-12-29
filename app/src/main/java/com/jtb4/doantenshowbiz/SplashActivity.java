package com.jtb4.doantenshowbiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import object.AppPara;
import utils.Const;
import utils.Faceb;
import utils.Osin;
import utils.Util;

public class SplashActivity extends Activity {
	//private ActionBar actionBar;
	private Osin osin;

/*	private View hiddenPanel;
	private ImageView img_close;
	private Button bttupdate;*/
	SharedPreferences settings;

	//private ImageButton btn_start,btn_share,btn_xemtieusu,btn_like;
	private Button btnbatdau, btntieusu, btnnhanxu;
	private ImageView btnstore, btnfb;
	int solan=0;
	private Faceb faceb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main_ui);
		osin=new Osin (this);
		faceb=new Faceb(this);
		faceb.initfacebook(savedInstanceState);
		/*hiddenPanel = findViewById(R.id.hidden_panel);
		img_close = (ImageView) findViewById(R.id.btnclose);
		bttupdate = (Button) findViewById(R.id.bttcapnhat);*/

	/*	btn_start = (ImageButton) findViewById(R.id.btn_start);
		btn_xemtieusu = (ImageButton) findViewById(R.id.btn_xemtieusu);
		btn_like = (ImageButton) findViewById(R.id.btn_like);
		btn_share = (ImageButton) findViewById(R.id.btn_share);
*/
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
			String randomstring=Util.get_randomString();
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("randomstring", randomstring);
			editor.putBoolean("firstrandom",false);
			editor.commit();

		}
		
       /* img_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 Animation bottomDown = AnimationUtils.loadAnimation(SplashActivity.this,
			                R.anim.slide_up);
			        hiddenPanel.startAnimation(bottomDown);
			        hiddenPanel.setVisibility(View.INVISIBLE);
				
				Intent i = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
			}
		});	 
        bttupdate.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				osin.UpdatethisApp();
			}
		});
*/
//new
		btnbatdau.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		btntieusu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(SplashActivity.this,
						PicActivity.class);
				startActivity(i);
				//Activity activity = (Activity) context;
				//startActivityForResult(i, 500);
				//overridePendingTransition(R.anim.slide_in_bottom, R.anim.null_animation);
				//finish();
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
		//new MyAsyncTask_onsplash(this,hiddenPanel).execute("");
        
       
	}

	public void show_update_request(){
		Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_down);
       /* hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);*/
	}
	
	public class MyAsyncTask_onsplash extends AsyncTask<String, Void, String> {	
		boolean isonline;
		int versionfromserver;
		private View hiddenPanel;
		private Context context;
		Osin osin;
	    public MyAsyncTask_onsplash(Context context,View hiddenPanel) {	 
	    	this.context=context;
	    	this.hiddenPanel=hiddenPanel;
	    	osin=new Osin(context);
	    }
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();	     
	        isonline=osin.isNetworkAvailable(); 
	        
	    }
	    @Override
	    protected String doInBackground(String... strings) {
	    	if (isonline)
	        {
	    		  String imsi=osin.get_ismi();
				  String device=osin.get_deviceinfor();
				 
	        	  osin.log2server(Const.loginstr+";solan:"+solan+";imsi:"+imsi+";dev:"+device);
	        	  solan++;
	        	  SharedPreferences.Editor editor = settings.edit();
				  editor.putInt("solan", solan);
				  editor.commit();
				  AppPara apppara=osin.get_Apppara();
				  String strsecure64=apppara.get_secure();		 
				  //String secureout = osin.decode(strsecure64);	
				  versionfromserver=Integer.parseInt(apppara.get_version());
				  
				  
				  
	        }	       
	    	      
	        return null;
	    }
	    public void thongbaoandexit(String str){
			 AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
				builder.setTitle("Thông báo");
				builder.setMessage(str)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {								
										 System.exit(0);
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			
		 }
	    @Override
	    protected void onPostExecute(String temp) {
	    	if (isonline)
		        {
			    	if (versionfromserver> Const.currversion)
					 {
						 Animation bottomUp = AnimationUtils.loadAnimation(context,
					                R.anim.slide_down);
					        hiddenPanel.startAnimation(bottomUp);
					        hiddenPanel.setVisibility(View.VISIBLE);
					 }
					 else
					 {
						 hiddenPanel.setVisibility(View.GONE);
						
						 Thread welcomeThread = new Thread() {
					            @Override
					            public void run() {
					                try {
					                    super.run();
					                    sleep(1000);  //Delay of 10 seconds
					                } catch (Exception e) {
		
					                } finally {
					                    Intent i = new Intent(SplashActivity.this,
												MainActivity.class);
					                    //startActivity(i);
					                    //finish();
					                }
					            }
					        };
						 welcomeThread.start();
					         
					 }
		        }
	        else
	        {
	        	
	        	thongbaoandexit("Vui lòng kết nối internet và thử lại");
	        	
	        }
	    }
	}
	
}

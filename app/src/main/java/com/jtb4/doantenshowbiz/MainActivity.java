package com.jtb4.doantenshowbiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import adapter.GridAdapter1;
import adapter.GridAdapter2;
import object.AppPara;
import object.Nghesi;
import object.Song;
import utils.Const;
import utils.Json;
import utils.Osin;
import utils.StreamingMediaPlayer;
import utils.Util;

public class MainActivity extends Activity implements  OnCompletionListener {

	//custom drawing view
	private DrawingView drawView;
	//buttons
	//private ImageButton drawBtn, eraseBtn, newBtn, saveBtn, opacityBtn;
	ImageButton btntieusu;
	//sizes
	private float smallBrush, mediumBrush, largeBrush;
	//TextView txtlevel,txtlabel;
	private ImageButton btn_bigger, btn_help1,  btn_clean,btn_sound, btn_refresh, btn_showdapan;
	private ProgressBar customProgress;
	//private TextView progressDisplay;
	
	private Timer timer,timer2;
	private LinearLayout linearLayouthiden;

	RelativeLayout rvuong;
	
	Nghesi nghesi;
	Osin osin;
	Json json;
	Util util;

	Context context;
	GridView gridView1;
	GridView gridView2;
	GridAdapter1 adapter1;
	GridAdapter2 adapter2;
	static final String[] alphabetlist = new String[] { 
		"A", "B", "C", "D", "E",
		"F", "G", "H", "I", "J",
		"K", "L", "M", "N", "O",
		"P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z"
	};
	/*
	 * 
	 ,
		"Á", "À", "Ả", "Ã", "Ạ",
		"Ô","Ố", "Ồ", "Ổ", "Ỗ", "Ộ",
		"Ê","Ế", "Ề", "Ể", "Ễ", "Ệ",
		"Ư", "Ú", "Ù", "Ũ", "Ủ","Ụ"
	 */
	
	List<String> Listkhen = Arrays.asList("Bạn tài quá đi", "Thật tuyệt vời", "Bạn đỉnh lắm đấy","Thật không thể tin nổi","Thật tuyệt","Tiếp tục nào","Good job !");
	
	ArrayList<String> list2 = new ArrayList<String>();
	ArrayList<String> list1 = new ArrayList<String>();
	String strC="-";
	String strQ="";
	ArrayList<Integer> stacklist=new ArrayList<Integer>();
	int current_position=0;
	int click_position=0;
	int fixlen=16;
	int namelen=0;
	int lendapan=0;
	String dapan="";
	static int t=30;//s
	//static int gioihan=0;
	//static int thoigian=30; //s 
	int myProgress = 100;
	int level=1;
	int best=1;
	ImageView img1;
	static int goiy=0;
	Boolean sound=true;
	Boolean dung=false;
	
	private AssetFileDescriptor sPlaySoundDescriptor_start = null;
	private AssetFileDescriptor sPlaySoundDescriptor_end = null;
	private AssetFileDescriptor sPlaySoundDescriptor_typing = null;
	
	private AssetFileDescriptor sPlaySoundDescriptor = null;
	private AssetFileDescriptor sFailSoundDescriptor = null;
	private AssetFileDescriptor sApplauseSoundDescriptor= null;
	private MediaPlayer mp = null;
	private MediaPlayer mpMusic = null;
	public static final int NEXT_SOUND = 1;
	public static final int APPLAUSE_SOUND = 2;	
	public static final int START_SOUND = 3;
	public static final int END_SOUND = 4;
	public static final int TYPING_SOUND = 5;
	ArrayList<AssetFileDescriptor> listsongpath;
	//private SongsManager songManager;
	//private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	Song Currentsong;
	private int currentSongIndex = 0; 
	private StreamingMediaPlayer audioStreamer;	
	//AlertDialog dialog;
	Dialog dialog ;
	int t1;
	private int request_Code500=500;//result
	private int request_Code600=600;//tieusu
	private int request_Code700=700;//exit
 
 	private static int numclick=0;
 	private InterstitialAd interstitial;
 	private InterstitialAd mInterstitialAd;
	private AdView adView;
	LinearLayout adContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		osin=new Osin(this);
		json= new Json(this);
		util=new Util(this);
		context=this;
		//get drawing view
		img1 = (ImageView) findViewById(R.id.imageView1);
		drawView = (DrawingView)findViewById(R.id.drawing);
		linearLayouthiden=(LinearLayout) findViewById(R.id.linearhiden) ;
		//rvuong = (RelativeLayout) findViewById(R.id.rvuong);
		int W= util.getWscreen(this);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(W,W*8/10);
		img1.setLayoutParams(layoutParams);
		drawView.setLayoutParams(layoutParams);
		drawView.setLayoutParams(layoutParams);


		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);


		
		btn_bigger = (ImageButton)findViewById(R.id.btn_bigger);
		btn_help1 = (ImageButton)findViewById(R.id.btn_help1);
		btn_showdapan = (ImageButton)findViewById(R.id.btnhelpall);
		btn_clean = (ImageButton)findViewById(R.id.btn_clean);
		btn_sound = (ImageButton)findViewById(R.id.btn_sound);
		btn_refresh = (ImageButton)findViewById(R.id.btn_refresh);
		btntieusu = (ImageButton)findViewById(R.id.btn_showtieusu);
		gridView1 = (GridView) findViewById(R.id.gridView1);	
		gridView2 = (GridView) findViewById(R.id.gridView2);
		//txtlevel= (TextView) findViewById(R.id.txtlevel);
		//txtlabel= (TextView) findViewById(R.id.txtlabel);
		customProgress = (ProgressBar)findViewById(R.id.customProgress);

		
		drawView.setErase(true);
		
		btn_bigger.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {	
				osin.makeToast("Bigger Brush");
				drawView.setBrushSize(largeBrush);
				btn_bigger.setVisibility(View.INVISIBLE);
			}
		});
		btn_help1.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {	
				if (goiy<3)
				{
					help_n(goiy);
					goiy++;
				}
				if (goiy==3)
				{
					//Load_admob_full();
				}
			}
		});

		btn_showdapan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*
				help_all();
				drawView.setVisibility(View.GONE);
				linearLayouthiden.setVisibility(View.VISIBLE);
				*/
				try
				{
					mpMusic.pause();
				}
				catch(Exception e){}
				audioPlayer_ketqua(END_SOUND);
				ComplteteGame();
				//show_congrat();
				//show_choilai();

				show_resultactivity();
				//Load_admob_full();
			}
		});
		btn_clean.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				drawView.setVisibility(View.GONE);
				btn_clean.setVisibility(View.INVISIBLE);
			}
		});
		btn_sound.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {	
				try
				{
					if(mpMusic.isPlaying()){	
							if(mpMusic!=null){
								sound=false;
								mpMusic.pause();
								btn_sound.setImageResource(R.drawable.ico_mute2);
							}					
					}else{				
						if(mpMusic!=null){		
							sound=true;
							mpMusic.start();
							btn_sound.setImageResource(R.drawable.ico_sound2);
						}					
					}		
				}
				catch(Exception e){}
			}
		});
		btn_refresh.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {	
				if (!dung)
				{
					level=1;
				}				
				setup_game();
			}
		});
		btntieusu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, TieusuActivity.class);
				intent.putExtra("nghesi",nghesi);
				Activity activity = (Activity) context;
				activity.startActivityForResult(intent, request_Code600);
				activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

			}
		});
		AppPara apppara=osin.get_Apppara();
		t=Integer.parseInt(apppara.get_para2());
		osin.logi("t="+apppara.get_para2());
		
		mpMusic = new MediaPlayer();	
		mpMusic.setOnCompletionListener(this); // Important	
		load_mp3();
		mpMusic.start();
		SharedPreferences settings =getSharedPreferences(Const.PREFS_NAME, 0);
		best=settings.getInt("best", 1);
		
		setup_game();


		//adContainer = (LinearLayout) findViewById(R.id.adViewContainer);
		//adContainer.setVisibility(View.GONE);
		LoadAdmob();
		//initfacebook(savedInstanceState);
		
		
		
	}
	@Override
	public void onDestroy()
	{
        super.onDestroy();
		try
		{
			timer.cancel();
			timer2.cancel();
		}
		catch (Exception e){}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		overridePendingTransition(R.anim.null_animation, R.anim.slide_out_bottom);
		if (requestCode == request_Code500) {
			if (resultCode == RESULT_OK) {
				String returnedResult = data.getData().toString();
				util.logi("returnedResult:"+returnedResult);
				if (returnedResult.contains("bnt_checked"))
				{
					help_all();
					drawView.setVisibility(View.GONE);

				}
				if (returnedResult.contains("btn_play"))
				{
					level=1;
					setup_game();
				}
			}
		}
		if (requestCode == request_Code600) {

		}
		if (requestCode == request_Code700) {
			if (resultCode == RESULT_OK) {
				String returnedResult = data.getData().toString();
				util.logi("returnedResult:" + returnedResult);
				if (returnedResult.contains("btn_thoat")) {
					osin.log2server("Game:ShowbizV2;best:"+Integer.toString(best)+";deveice:");
					finish();
				}
			}
		}
	}
	private void show_choilai(){
		// Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        // Set dialog title
        //dialog.setTitle("GAME OVER");
        
        // set values for custom dialog components - text, image and button
        TextView textscore = (TextView) dialog.findViewById(R.id.txtscore);
        TextView textbest = (TextView) dialog.findViewById(R.id.txtbest);
        textscore.setText("SCORE : " + Integer.toString(level));
        textbest.setText("BEST : "+ Integer.toString(best));
        //Typeface type = Typeface.createFromAsset(getAssets(),"fonts/elegant_ink.ttf");
        //textscore.setTypeface(type);
        //textbest.setTypeface(type);
        
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        ImageView image_xemdapan = (ImageView) dialog.findViewById(R.id.btn_xemdapan);
        ImageView image_sharefb = (ImageView) dialog.findViewById(R.id.btn_sharefb);
        image.setImageResource(R.drawable.ico_gameover);
        
        dialog.show();
         
        ImageButton btnstart = (ImageButton) dialog.findViewById(R.id.btnstart);
        // if decline button is clicked, close the custom dialog
        btnstart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
            	level=1;
				setup_game();
                dialog.dismiss();
            }
        });
        image_xemdapan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	//Load_admob_full();
            	help_all();  
            	drawView.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        image_sharefb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	//postPhoto();
            	osin.log2server(Const.share_app_fb+";device:");
                //dialog.dismiss();
            }
        });
	}
	private void load_mp3(){
		listsongpath=new ArrayList<AssetFileDescriptor>();
		 AssetFileDescriptor soundFileDescriptor = null;	
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f1) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f2) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f3) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f4) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f5) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f6) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f7) );		 
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f8) );
		 listsongpath.add(context.getResources().openRawResourceFd(R.raw.f9) );

	}
	private void setup_game(){
		//Nghesi nghesi=new Nghesi("Trương Thế Vinh","truongthevinh","","","");
		//nghesi=osin.get_nghesi_moi(level);
		nghesi=json.get1Nghesi(level);
		util.logi("nghesi:"+nghesi);
		//osin.logi("nghesi:"+ nghesi.getname());
		AQuery aq = new AQuery(this);
		 //aq.id(drawView).image(nghesi.geturlimg(), true, true, 0, R.drawable.img_truongthevinh, null, AQuery.FADE_IN);
		 aq.id(img1).image(nghesi.geturlimg(), true, true, 0, R.drawable.ic_launcher, null, AQuery.FADE_IN);
		try
		{
			drawView.startNew();
		}
		catch (Exception e){}
		 
		String name=chuan_str(nghesi.getname());
		dapan=name;
		lendapan=name.length();
		namelen=lendapan;
		list1.clear();
		list2.clear();
		current_position=0;
		click_position=0;	
		goiy=0;
		t=t-level;
				util.logi("thoi gian t="+t);
		myProgress=100;
		 
		
		drawView.setVisibility(View.VISIBLE);
		drawView.setBrushSize(smallBrush);
		//txtlevel.setText(Integer.toString(level));
		
		if (!dung){
			btn_bigger.setVisibility(View.VISIBLE);
			//btn_showdapan.setVisibility(View.VISIBLE);
			btn_clean.setVisibility(View.VISIBLE);
		}
		dung=false;		
		
		for (int i=0;i<namelen;i++)
		{
			list1.add(strQ);		
		}
		for (int i=0;i<namelen;i++)
		{
			list2.add(name.substring(i,i+1));		
		}
		for (int i=namelen;i<fixlen;i++)
		{
			Random rand = new Random();
			int  n = rand.nextInt(alphabetlist.length);
			String R=alphabetlist[n];
			list2.add(R);
			
		}
		
		long seed = System.nanoTime();
		Collections.shuffle(list2, new Random(seed));
		
		
		adapter1 = new GridAdapter1(this,list1,1);
		adapter2 = new GridAdapter2(this,list2,2);
	
		gridView1.setAdapter(adapter1);
		gridView2.setAdapter(adapter2);	
		//btn_refresh.setVisibility(View.GONE);
		linearLayouthiden.setVisibility(View.GONE);
		gridView1.setEnabled(true);
		gridView2.setEnabled(true);
		
		gridView1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {			
				processGrid1_click(position);			
			
			}
		});
		
		gridView2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				audioPlayer_ketqua(TYPING_SOUND);
				processGrid2_click(position);		
				
			}
		});	
		
		 //new ShowCustomProgressBarAsyncTask().execute();

		sound=false;//test
		 if (sound)
		 {
			 	Random rand=new Random ();
				currentSongIndex=rand.nextInt(listsongpath.size()-1);
				playSongOff(currentSongIndex);
		 }
		 set_timer();
	}
	private void set_timer(){
		 timer = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                    	 myProgress--;
	                    	  customProgress.setProgress(myProgress);
	                          customProgress.setSecondaryProgress(myProgress-1);
	                          //progressDisplay.setText(String.valueOf(myProgress)+"%");
	                    	if (myProgress<=0)
	                    	{
	                    		timer.cancel();
	                    		if (dung)
	                    		{	                    			
	                    			setup_game();
	                    		}
	                    		else
	                    		{	                    			
		                    		try
		                    		{
		                    			mpMusic.pause();
		                    		}
		                    		catch(Exception e){}
		                    		audioPlayer_ketqua(END_SOUND);
		                    		ComplteteGame();
		                    		//show_congrat();
		                    		//show_choilai();

									show_resultactivity();
	                    		}
	                    		
	                    	}
	                    	
	                    }
	                });
	            }
	        }, 100, t*10);
	}
	@Override
    public void onPause() {
        super.onPause();
       try
       {
    	   if(mpMusic!=null){				
    		   mpMusic.pause();								
			}	
       }
       catch (Exception e){}
    }

    @Override
    public void onStop() {
        super.onStop();
        try
        {
        	 if(mpMusic!=null ){				
      		   mpMusic.pause();								
  			}	
        }
        catch (Exception e){}
    }
    @Override
    protected void onResume() {
        super.onResume();
        //updateUI();    
		try {
			if(mpMusic!=null & sound){				
	      		   mpMusic.start();							
	  			}		
		 }
        catch (Exception e){}
    }

    
	private void ComplteteGame(){		 
		//if (dung)
		{
			timer.cancel();
		}
		gridView1.setEnabled(false);
		gridView2.setEnabled(false);
		 //btn_refresh.setVisibility(View.VISIBLE);
		linearLayouthiden.setVisibility(View.VISIBLE);
		 if (level>best){
			 best=level;
			 SharedPreferences settings =getSharedPreferences(Const.PREFS_NAME, 0);
		        SharedPreferences.Editor editor = settings.edit();
		        editor.putInt("best", best);        
		        editor.commit();	
		        //osin.log2server("Game:Showbiz;best:"+Integer.toString(best)+";deveice:");
		 }
		 
		 
		//osin.makeToast("fail");
	}
	private void processGrid1_click(int p)
	{
		if (current_position>0)
		{
			//TextView tv = (TextView) gridView1.getChildAt(p);
			//tv.setBackgroundColor(Color.parseColor("#293643"));
			
			
			String C_chon=list1.get(current_position-1); 		
			list1.set(current_position-1, strQ);
			current_position--;
			int topstack=stacklist.get(stacklist.size()-1);
			stacklist.remove(stacklist.size()-1);
			list2.set(topstack, C_chon);
			
			adapter1.notifyDataSetChanged();	
			adapter2.notifyDataSetChanged();
			gridView1.setAdapter(adapter1);
			gridView2.setAdapter(adapter2);	
			
		}
	}
	private void processGrid2_click(int p)
	{	
		//playsound_typing(APPLAUSE_SOUND);
		if (lendapan>current_position){		
			click_position=p;
			String C_chon=list2.get(p) ;
			if (C_chon.equals(strC)) return;
			list2.set(p, strC);		
			list1.set(current_position, C_chon);
			
			current_position++;
			stacklist.add(p);
			
			adapter1.notifyDataSetChanged();	
			adapter2.notifyDataSetChanged();
			gridView1.setAdapter(adapter1);
			gridView2.setAdapter(adapter2);
			
		}
		if (lendapan==current_position){
			check_result();
		}
	}
	private void help_n(int n){
		String tonename=dapan;
		list1.set(n, tonename.substring(n,n+1)+" ");
		
		adapter1.notifyDataSetChanged();	
		adapter2.notifyDataSetChanged();
		gridView1.setAdapter(adapter1);
		gridView2.setAdapter(adapter2);	
	}
	private void help_all(){
		String tonename=dapan;
		//osin.logi("tone len:"+tonename.length()+";list size="+ Integer.toString(list1.size()));
		for (int i=0;i<list1.size();i++)
		{
			list1.set(i, tonename.substring(i,i+1)+" ");	
			//osin.logi("i:"+i+";"+tonename.substring(i,i+1));
		}	
		
		adapter1.notifyDataSetChanged();	
		adapter2.notifyDataSetChanged();
		gridView1.setAdapter(adapter1);
		gridView2.setAdapter(adapter2);	
		gridView2.setEnabled(false);
	}
	private void check_result(){
		String answer="";
		for (int i=0;i<namelen;i++)
		{
			answer=answer+list1.get(i);			
		}
		if(answer.toUpperCase().equals(dapan))
		{
			audioPlayer_ketqua(APPLAUSE_SOUND);
			Random rand = new Random();
			int  i = rand.nextInt(Listkhen.size()-1) ;
			//osin.makeToast(Listkhen.get(i));
			dung=true;
			show_congrat2();
			t1=0;
			timer2 = new Timer();

			timer2.schedule(new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							t1=t1+1;
							//util.logi("t1="+Integer.toString(t1));
							if (t1>=20)
							{
								timer2.cancel();
								try
								{
									dialog.dismiss();
									//playNext();
								}
								catch (Exception e){};
							}
						}
					});
				}
			}, 10, 80);


			ComplteteGame();			
			//setup_game();			
			drawView.setVisibility(View.GONE);
			
			level++;
		}
		else
		{
			//osin.makeToast("inCorrext");
		}
	}
	private void show_congrat2(){
		// Create custom dialog object
		dialog = new Dialog(this);
		// Include dialog.xml file
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.congra);
		ImageButton btnimg= (ImageButton) dialog.findViewById(R.id.imageView1);
		btnimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();

	}
	private void show_resultactivity(){
		Intent intent = new Intent(context, ResultActivity.class);
		intent.putExtra("nghesi",nghesi);
		intent.putExtra("best",best);
		intent.putExtra("score",level);
		Activity activity = (Activity) context;
		activity.startActivityForResult(intent, request_Code500);
		activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	private String chuan_str(String str){
		return str.replace(" ", "").toUpperCase().trim();
	}


	//user clicked paint
	public void paintClicked(View view){
		//use chosen color

		//set erase false
		drawView.setErase(false);
		drawView.setPaintAlpha(100);
		drawView.setBrushSize(drawView.getLastBrushSize());

		/*
		if(view!=currPaint){
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			drawView.setColor(color);
			//update ui
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint=(ImageButton)view;
		}
		*/
	}
/*
	@Override
	public void onClick(View view){

		if(view.getId()==R.id.draw_btn){
			//draw button clicked
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Brush size:");
			brushDialog.setContentView(R.layout.brush_chooser);
			//listen for clicks on size buttons
			ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setErase(false);
					drawView.setBrushSize(smallBrush);
					drawView.setLastBrushSize(smallBrush);
					brushDialog.dismiss();
				}
			});
			ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setErase(false);
					drawView.setBrushSize(mediumBrush);
					drawView.setLastBrushSize(mediumBrush);
					brushDialog.dismiss();
				}
			});
			ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setErase(false);
					drawView.setBrushSize(largeBrush);
					drawView.setLastBrushSize(largeBrush);
					brushDialog.dismiss();
				}
			});
			//show and wait for user interaction
			brushDialog.show();
		}
		else if(view.getId()==R.id.erase_btn){
			//switch to erase - choose size
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Eraser size:");
			brushDialog.setContentView(R.layout.brush_chooser);
			//size buttons
			ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(smallBrush);
					brushDialog.dismiss();
				}
			});
			ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(mediumBrush);
					brushDialog.dismiss();
				}
			});
			ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(largeBrush);
					brushDialog.dismiss();
				}
			});
			brushDialog.show();
		}
		else if(view.getId()==R.id.new_btn){
			//new button
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New drawing");
			newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
			newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					drawView.startNew();
					dialog.dismiss();
				}
			});
			newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			newDialog.show();
		}
		else if(view.getId()==R.id.save_btn){
			//save drawing
			AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
			saveDialog.setTitle("Save drawing");
			saveDialog.setMessage("Save drawing to device Gallery?");
			saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					//save drawing
					drawView.setDrawingCacheEnabled(true);
					//attempt to save
					String imgSaved = MediaStore.Images.Media.insertImage(
							getContentResolver(), drawView.getDrawingCache(),
							UUID.randomUUID().toString()+".png", "drawing");
					//feedback
					if(imgSaved!=null){
						Toast savedToast = Toast.makeText(getApplicationContext(), 
								"Drawing saved to Gallery!", Toast.LENGTH_SHORT);
						savedToast.show();
					}
					else{
						Toast unsavedToast = Toast.makeText(getApplicationContext(), 
								"Oops! Image could not be saved.", Toast.LENGTH_SHORT);
						unsavedToast.show();
					}
					drawView.destroyDrawingCache();
				}
			});
			saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			saveDialog.show();
		}
		else if(view.getId()==R.id.opacity_btn){
			//launch opacity chooser
			final Dialog seekDialog = new Dialog(this);
			seekDialog.setTitle("Opacity level:");
			seekDialog.setContentView(R.layout.opacity_chooser);
			//get ui elements
			final TextView seekTxt = (TextView)seekDialog.findViewById(R.id.opq_txt);
			final SeekBar seekOpq = (SeekBar)seekDialog.findViewById(R.id.opacity_seek);
			//set max
			seekOpq.setMax(100);
			//show current level
			int currLevel = drawView.getPaintAlpha();
			seekTxt.setText(currLevel+"%");
			seekOpq.setProgress(currLevel);
			//update as user interacts
			seekOpq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					seekTxt.setText(Integer.toString(progress)+"%");
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {}

			});
			//listen for clicks on ok
			Button opqBtn = (Button)seekDialog.findViewById(R.id.opq_ok);
			opqBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					drawView.setPaintAlpha(seekOpq.getProgress());
					seekDialog.dismiss();
				}
			});
			//show dialog
			seekDialog.show();
		}
	}
	*/
	
	
	public class ShowCustomProgressBarAsyncTask extends AsyncTask<Void, Integer, Void> {

        int myProgress;

        @Override
        protected void onPreExecute() {
            myProgress = 100;
        }

        @SuppressLint("NewApi")
		@Override
        protected Void doInBackground(Void... params) {
            //while(myProgress<100){
        	while(myProgress>0){
                myProgress--;
                publishProgress(myProgress);
                SystemClock.sleep(200);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            customProgress.setProgress(values[0]);
            customProgress.setSecondaryProgress(values[0] - 1);
            //progressDisplay.setText(String.valueOf(myProgress)+"%");
          
        }

        @Override
        protected void onPostExecute(Void result) {
        	osin.makeToast("Fail");
        	
        }
    }
	
	public void audioPlayer_ketqua(int soundType){
	    //set up MediaPlayer    
	    mp = new MediaPlayer();
	    AssetFileDescriptor soundFileDescriptor = null;
	    try {
	        if (sFailSoundDescriptor == null) {
	            sFailSoundDescriptor = context.getResources().
	                    openRawResourceFd(R.raw.audionext);
	        }
	        if (sApplauseSoundDescriptor == null) {
	            sApplauseSoundDescriptor = context.getResources().
	                    openRawResourceFd(R.raw.audiocorrect);
	        }
	        
	        if (sPlaySoundDescriptor_start == null) {
	        	sPlaySoundDescriptor_start = context.getResources().
	                    openRawResourceFd(R.raw.audiostart);
	        }
	        if (sPlaySoundDescriptor_end == null) {
	        	sPlaySoundDescriptor_end = context.getResources().
	                    openRawResourceFd(R.raw.audioend);
	        }
	        if (sPlaySoundDescriptor_typing == null) {
	        	sPlaySoundDescriptor_typing = context.getResources().
	                    openRawResourceFd(R.raw.audiotyping);
	        }
	        
	        
	        switch (soundType) {
	            case NEXT_SOUND:
	                soundFileDescriptor = sFailSoundDescriptor;
	                break;
	            case APPLAUSE_SOUND:
	                soundFileDescriptor = sApplauseSoundDescriptor;
	                break;
	            case START_SOUND:
	                soundFileDescriptor = sPlaySoundDescriptor_start;
	                break;
	            case END_SOUND:
	                soundFileDescriptor = sPlaySoundDescriptor_end;
	                break;
	            case TYPING_SOUND:
	                soundFileDescriptor = sPlaySoundDescriptor_typing;
	                break;
	        }
	        
	        
	        
	        mp.reset();
	        mp.setDataSource(soundFileDescriptor.getFileDescriptor(),
	                        soundFileDescriptor.getStartOffset(),
	                        soundFileDescriptor.getDeclaredLength());
	        mp.prepare();
	        mp.setOnPreparedListener(new OnPreparedListener() {
	            public void onPrepared(MediaPlayer player) {
	                player.seekTo(0);
	                player.start();
	            }
	        });  
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void playbgmusic(int soundType){
	    //set up MediaPlayer    
	    mp = new MediaPlayer();
	    AssetFileDescriptor soundFileDescriptor = null;
	    try {
	        if (sFailSoundDescriptor == null) {
	            sFailSoundDescriptor = context.getResources().
	                    openRawResourceFd(R.raw.audionext);
	        }
	        if (sApplauseSoundDescriptor == null) {
	            sApplauseSoundDescriptor = context.getResources().
	                    openRawResourceFd(R.raw.audiocorrect);
	        }
	        
	        if (sPlaySoundDescriptor_start == null) {
	        	sPlaySoundDescriptor_start = context.getResources().
	                    openRawResourceFd(R.raw.audiostart);
	        }
	        if (sPlaySoundDescriptor_end == null) {
	        	sPlaySoundDescriptor_end = context.getResources().
	                    openRawResourceFd(R.raw.audioend);
	        }
	        if (sPlaySoundDescriptor_typing == null) {
	        	sPlaySoundDescriptor_typing = context.getResources().
	                    openRawResourceFd(R.raw.audiotyping);
	        }
	        
	        
	        switch (soundType) {
	            case NEXT_SOUND:
	                soundFileDescriptor = sFailSoundDescriptor;
	                break;
	            case APPLAUSE_SOUND:
	                soundFileDescriptor = sApplauseSoundDescriptor;
	                break;
	            case START_SOUND:
	                soundFileDescriptor = sPlaySoundDescriptor_start;
	                break;
	            case END_SOUND:
	                soundFileDescriptor = sPlaySoundDescriptor_end;
	                break;
	            case TYPING_SOUND:
	                soundFileDescriptor = sPlaySoundDescriptor_typing;
	                break;
	        }
	        
	        
	        
	        mp.reset();
	        mp.setDataSource(soundFileDescriptor.getFileDescriptor(),
	                        soundFileDescriptor.getStartOffset(),
	                        soundFileDescriptor.getDeclaredLength());
	        mp.prepare();
	        mp.setOnPreparedListener(new OnPreparedListener() {
	            public void onPrepared(MediaPlayer player) {
	                player.seekTo(0);
	                player.start();
	            }
	        });  
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void  playSongOff(int songIndex){	
		try {
			AssetFileDescriptor soundFileDescriptor = null;
			soundFileDescriptor=listsongpath.get(songIndex);
			mpMusic.reset();
			//mp.setDataSource(songsList.get(songIndex).get("songPath"));
			mpMusic.setDataSource(soundFileDescriptor.getFileDescriptor(),
                     soundFileDescriptor.getStartOffset(),
                     soundFileDescriptor.getDeclaredLength());
			mpMusic.prepare();
			mpMusic.start();			
			//String songTitle = songsList.get(songIndex).get("songTitle");        	
			//btn_sound.setImageResource(R.drawable.ico_sound);	
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onCompletion(MediaPlayer arg0) {	
		
		if (currentSongIndex<listsongpath.size()-1)
		{
			currentSongIndex++;
		}
		else
		{
			currentSongIndex=0;
		}		
	
			playSongOff(currentSongIndex);
		
	}
	private void show_congrat(){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Het gio !");
		//builder.setMessage("Click Next de tiep tuc");
		builder.setPositiveButton("Choi lai",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int id) {	
				level=1;
				setup_game();
			}
		});
		dialog = builder.create();
		dialog.getWindow().getAttributes().windowAnimations = 
		R.style.DialogAnimation;
		dialog.show();				
		
	}
	
	
	
	 private void LoadAdmob()
		{
		//BEGIN ADD ADMOb
				// Prepare the Interstitial Ad
				interstitial = new InterstitialAd(MainActivity.this);
				// Insert the Ad Unit ID
				interstitial.setAdUnitId("ca-app-pub-8623108209004118/7004623180");
				
				//Locate the Banner Ad in activity_main.xml
				adView = (AdView) this.findViewById(R.id.adView);
			//adView.setVisibility(View.GONE);
				// Request for Ads
				AdRequest adRequest = new AdRequest.Builder()	
				//.addTestDevice("DEE5DD83A2C2CE7B76777723DEE86B0B")
				// Add a test device to show Test Ads
				 	//.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				 	//.addTestDevice("")
				.build();			
				// Load ads into Banner Ads
				adView.loadAd(adRequest);		
				// Load ads into Interstitial Ads
				interstitial.loadAd(adRequest);		
				// Prepare an Interstitial Ad Listener
				interstitial.setAdListener(new AdListener() {
					public void onAdLoaded() {
						// Call displayInterstitial() function
						//adView.setVisibility(View.VISIBLE);
						adContainer.setVisibility(View.VISIBLE);
						displayInterstitial();
					}
				});//End Admod	
		}
		public void displayInterstitial() {	
			if (interstitial.isLoaded()) {
				interstitial.show();
			}
		}
		
		
		
		 private void Load_admob_full(){
			 /*
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
		        */
		    }
		    private void requestNewInterstitial() {
		        AdRequest adRequest = new AdRequest.Builder()
		                  .addTestDevice("DEE5DD83A2C2CE7B76777723DEE86B0B")
		                  .build();
		        mInterstitialAd.loadAd(adRequest);
		    }

		    
		    

			    
			    
			    
			    @Override
			    public void onBackPressed() {
				 /*
					numclick++;
					Intent intent = new Intent(context, ExitActivity.class);
					Activity activity = (Activity) context;
					activity.startActivityForResult(intent, request_Code700);
					activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
*/
			    	if (numclick>2){
			    		finish();
			    	}
			    	else
			    	{
			    		//Load_admob_full();
			    	}
			    	osin.log2server("Game:Showbiz;best:"+Integer.toString(best)+";deveice:");	
			    	xacnhanExit(); 
			        return;


			    }
			 
			 private void xacnhanExit(){		
				 DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			                switch (which){
			                case DialogInterface.BUTTON_POSITIVE:
			                	
			                	      Thoat();
			                    break;

			                case DialogInterface.BUTTON_NEGATIVE:
			                		
			                    break;
			                }
			            }
			        };
			        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
			        builder1.setTitle("Xác nhận");	      
			        String ndug="Bạn có muốn thoát không ?";
			        builder1.setMessage(ndug).setPositiveButton("Có", dialogClickListener)
			            .setNegativeButton("Không", dialogClickListener).show();
				 
			 }
			 private void Thoat(){		 
				 	//Load_admob_full();
				 try
				 {
					 timer.cancel();
					 timer2.cancel();
				 }
				 catch (Exception e){}
					finish();
			 }

}

package utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jtb4.doantenshowbiz.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import object.AppPara;
import object.MusicquizPlayer;
import object.Nghesi;
import object.Song;
import object.Xephang;


public class Osin {	
	private static Context mContext;
	 int fixlen=20;
	public Osin(Context applicationContext) {
		super();
	    this.mContext = applicationContext;	   	    
	}
	/*
	public Osin() {
		super();	   
	}
	*/
	public static Context getContext() {
        return mContext;        
    }
	@SuppressLint("NewApi")
	public ArrayList<Nghesi> getNghesi(int intlevel){  
		JSONArray alarm = new JSONArray();
	 	ArrayList<Nghesi> studentArray1 = new ArrayList<Nghesi>();    
  		
  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
  		StrictMode.setThreadPolicy(policy);	  	          		
  		try {			
  			studentArray1.clear();          			
  			        try {
  			        	JSONfunctions jParser = new JSONfunctions();
  			        	String fullUrl=Const.urlgetnghesi+Integer.toString(intlevel)+"&device="+get_deviceinfor();
  			        	//String fullUrl="http://123.30.100.126:8081/Restapi/rest/Musicquiz/get5song?level=1";
  			        	logi(fullUrl);
  			        	alarm = jParser.getJSONfromURL(fullUrl);
  			        } catch (Exception e) {
  			            //e.printStackTrace();
  			        	logi("loi:"+e.toString());
  			        }    
  			      logi(Integer.toString(alarm.length()));
  			for(int i = 0; i < alarm.length(); i++){  			
  				JSONObject c = alarm.getJSONObject(i);   
  				String name = c.getString("name");
  				String nameid = c.getString("nameid");
  				String type1 = c.getString("type1");  			
  				String imgurl = c.getString("urlimg");			
  				String mota = c.getString("mota");
  				studentArray1.add(new Nghesi(name, nameid,type1, imgurl,mota));
  				}
  			
  			
  		} catch (JSONException e) {
  			//e.printStackTrace();
  		}     
  		return studentArray1;
  	    
     }
	public Nghesi get_nghesi_moi(int diemso){  
		ArrayList<Nghesi> list=getNghesi(diemso);
		if (list.size()==0)
		{
			list=getNghesi(10000);			
		}
		return list.get(0);
	}
	
	public ArrayList<MusicquizPlayer> getTopplayer(String  device){  
		JSONArray alarm = new JSONArray();
	 	ArrayList<MusicquizPlayer> studentArray1 = new ArrayList<MusicquizPlayer>();
    	//String type1=intlevel.replace(" ", "_");;//String type1="Tophit";
  		
  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
  		StrictMode.setThreadPolicy(policy);	  	          		
  		try {			
  			studentArray1.clear();          			
  			        try {
  			        	JSONfunctions jParser = new JSONfunctions();
  			        	String fullUrl=Const.urlTopplayer+device;
  			        	//String fullUrl="http://123.30.100.126:8081/Restapi/rest/Musicquiz/get5song?level=1";
  			        	//logi(fullUrl);
  			        	alarm = jParser.getJSONfromURL(fullUrl);
  			        } catch (Exception e) {
  			            //e.printStackTrace();
  			        	logi("loi:"+e.toString());
  			        }    
  			      logi(Integer.toString(alarm.length()));
  			for(int i = 0; i < alarm.length(); i++){  			
  				JSONObject c = alarm.getJSONObject(i);   
  				String device1 = c.getString("device"); 			
  				int point1 = c.getInt("point");    				
  					
  				studentArray1.add(new MusicquizPlayer(device1,point1));
  				}
  		} catch (JSONException e) {
  			//e.printStackTrace();
  		}     
  		return studentArray1;
  	    
     }
	public String converturl(String toneid){
		String result="";	
		String tam1=toneid.substring(0, 3);
		String tam2=toneid.substring(3, 6);
		String tam3=toneid.substring(6, 7);
		String tam4=toneid.substring(7, 11);
		String tam5=toneid.substring(11, 15);
		String tam6=toneid.substring(15, 18);
		result=tam1+"/"+tam2+"/"+tam3+"/"+tam4+"/"+tam5+"/"+tam6;
		Log.i("Funring", result);
		return result; 
	 }
	
	 public void logi(String str){		 
		//Log.i("MyActivity:",str);
	 }
	 
	 public void print_list(ArrayList<String> list){
		 int i=0;
		 for  (String item : list){
			 logi(i+":"+item);
			 i++;
		 }
			 
	 }
	 public void print_listsong(ArrayList<Song> listsong){
		 int i=0;
		 for  (Song item : listsong){
			 logi(i+":"+item.getTonename());
			 i++;
		 }
			 
	 }
	 public void log2server(String logstr){ 		
		    if (android.os.Build.VERSION.SDK_INT > 9) {
		  	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	      StrictMode.setThreadPolicy(policy);
		  	    }
		 try {
				HttpClient httpclient = new DefaultHttpClient();
				//String str1=Constants.urllog+"?imsi="+imsi+"&action="+action+"&smsdata="+smsdata+"&cell="+cell;
				String str1=Const.urlpostlog2server+logstr+":"+get_deviceinfor();
				//Log.i("MyActivity", str1);
				HttpPost httppost = new HttpPost(str1);       				
				
				List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				namevaluepairs.add((NameValuePair) new BasicNameValuePair("imsi","123"));
				
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				HttpResponse response = httpclient.execute(httppost);		
				} catch (ClientProtocolException e) {
				    // TODO Auto-generated catch block
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				}
		 
	 }
	 public void insertdevice(){ 			
		    if (android.os.Build.VERSION.SDK_INT > 9) {
		  	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	      StrictMode.setThreadPolicy(policy);
		  	    }
		 try {
				HttpClient httpclient = new DefaultHttpClient();
				//String str1=Constants.urllog+"?imsi="+imsi+"&action="+action+"&smsdata="+smsdata+"&cell="+cell;
				String str1=Const.urlinsertdevice+get_deviceinfor();
				//Log.i("MyActivity", str1);
				HttpPost httppost = new HttpPost(str1);       				
				
				List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				namevaluepairs.add((NameValuePair) new BasicNameValuePair("imsi","123"));
				
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				HttpResponse response = httpclient.execute(httppost);		
				} catch (ClientProtocolException e) {
				    // TODO Auto-generated catch block
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				}
		 
	 }
	 public void updatepoint(String newpoint){ 			
		    if (android.os.Build.VERSION.SDK_INT > 9) {
		  	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	      StrictMode.setThreadPolicy(policy);
		  	    }
		 try {
				HttpClient httpclient = new DefaultHttpClient();
				//String str1=Constants.urllog+"?imsi="+imsi+"&action="+action+"&smsdata="+smsdata+"&cell="+cell;
				String str1=Const.urlupdatepoint+get_deviceinfor()+"&point="+newpoint;
				//Log.i("MyActivity", str1);
				HttpPost httppost = new HttpPost(str1);       				
				
				List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				namevaluepairs.add((NameValuePair) new BasicNameValuePair("imsi","123"));
				
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				HttpResponse response = httpclient.execute(httppost);		
				} catch (ClientProtocolException e) {
				    // TODO Auto-generated catch block
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				}
		 
	 }
	/*
	 public void postsongdachoi(Song song,String chude){ 			
		    if (android.os.Build.VERSION.SDK_INT > 9) {
		  	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	      StrictMode.setThreadPolicy(policy);
		  	    }
		 try {
				HttpClient httpclient = new DefaultHttpClient();
				//String str1=Constants.urllog+"?imsi="+imsi+"&action="+action+"&smsdata="+smsdata+"&cell="+cell;
				String str1=Const.postsongdachoi+get_deviceinfor()+"&tonename="+song.getTonename().trim().replace(" ", "_")+"&toneid="+song.getToneid().trim()+"&chude="+chude;
				//Log.i("MyActivity", str1);
				HttpPost httppost = new HttpPost(str1);       				
				
				List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				namevaluepairs.add((NameValuePair) new BasicNameValuePair("imsi","123"));
				
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				HttpResponse response = httpclient.execute(httppost);		
				} catch (ClientProtocolException e) {
				    // TODO Auto-generated catch block
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				}
		 
	 }
	 */
	 public String get_deviceinfor(){
		 /*
		 String android_id = Secure.getString(getContext().getContentResolver(),
                 Secure.ANDROID_ID); 
		 return android_id;
		 */
		 SharedPreferences settings =mContext.getSharedPreferences(Const.PREFS_NAME, 0);
		 String device=settings.getString("randomstring","");
		 return device;
	 }
	 public boolean isNetworkAvailable() {	
			ConnectivityManager connectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager
					.getActiveNetworkInfo();
			return activeNetworkInfo != null;
		}
	 public void thongbaoandexit(){
		 String noidung=mContext.getResources().getString(R.string.thongbao);
		 AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle(mContext.getResources().getString(R.string.titlethongbao));
			builder.setMessage(noidung)
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
	 public void UpdatethisApp(){
		 final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
			 try {
				 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			 } catch (android.content.ActivityNotFoundException anfe) {
				 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
			 }
	}
	public void GoPLay(){
		final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
		try {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=JTB-4")));
		} catch (android.content.ActivityNotFoundException anfe) {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=JTB-4")));
		}
	}
	 public AppPara get_Apppara(){    
		 String device=get_deviceinfor();
		 JSONArray alarm = new JSONArray();
		 AppPara app=new AppPara();
	   		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  		StrictMode.setThreadPolicy(policy);
	  	
	   			        try {
	   			        	
	   			        	JSONfunctions jParser = new JSONfunctions();
	   			        	//Log.i("MyActivity", Constants.urlezinfor);
	   			        	String url="http://123.30.100.126:8081/Restapi/rest/Musicquiz/getparaapp/showbizgame?device="+device;
	   			        	//alarm = jParser.getJSONfromURL(Const.apppara_v5+"funring");	
	   			        	alarm = jParser.getJSONfromURL(url);
	   			        } catch (Exception e) {
	   			            //e.printStackTrace();
	   			        }   
	   			     try  { 
	   				JSONObject c = alarm.getJSONObject(0);
	   				app.set_secure(c.getString("secure"));
	   				app.set_version(c.getString("version"));
	   				app.set_para1(c.getString("para1"));
	   				app.set_para2(c.getString("para2"));
	   				app.set_para3(c.getString("para3"));
	   				app.set_para4(c.getString("para4"));	   			
	   				
	           		} catch (JSONException e) {
	           			//e.printStackTrace();
	           		}   
	   			     return app;
	      }
	 	public void post2serversongketqua(String ketqua,Song song){
		 
		// theloai=theloai.replace(" ", "_");
		    if (android.os.Build.VERSION.SDK_INT > 9) {
		  	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  	      StrictMode.setThreadPolicy(policy);
		  	    }
		 try {
				HttpClient httpclient = new DefaultHttpClient();
				//String str1=Constants.urllog+"?imsi="+imsi+"&action="+action+"&smsdata="+smsdata+"&cell="+cell;
				String str1=Const.urlpostketqua+ketqua+"&toneid="+song.getToneid().trim()
						+"&tonename="+song.getTonename().trim().replace(" ", "_")
						+"&tonecode="+song.getTonecode().trim().replace(" ", "_")
						+"&singer="+song.getSinger().trim().replace(" ", "_")
						+"&downtimes="+song.getDowntimes().trim().replace(" ", "_")
						+"&prices="+song.getPrice()
						+"&device="+get_deviceinfor();
				logi(str1);
				HttpPost httppost = new HttpPost(str1);       				
				
				List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				namevaluepairs.add((NameValuePair) new BasicNameValuePair("imsi","123"));
				
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				HttpResponse response = httpclient.execute(httppost);		
				} catch (ClientProtocolException e) {
				    // TODO Auto-generated catch block
				} catch (IOException e) {
				    // TODO Auto-generated catch block
				}
		 
	 }
	 	public String get_xephang(String  device, String point){  
			JSONArray alarm = new JSONArray();
		 	//ArrayList<MusicquizPlayer> studentArray1 = new ArrayList<MusicquizPlayer>();
			Xephang studentArray1 = new Xephang();
	    	//String type1=intlevel.replace(" ", "_");;//String type1="Tophit";
	  		
	  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  		StrictMode.setThreadPolicy(policy);	  	          		
	  		try {			
	  			//studentArray1.clear();          			
	  			        try {
	  			        	JSONfunctions jParser = new JSONfunctions();
	  			        	String fullUrl=Const.urlgetxephang+device+ "&point="+point;
	  			        	//String fullUrl="http://123.30.100.126:8081/Restapi/rest/Musicquiz/get5song?level=1";
	  			        	logi(fullUrl);
	  			        	alarm = jParser.getJSONfromURL(fullUrl);
	  			        } catch (Exception e) {
	  			            //e.printStackTrace();
	  			        	logi("loi:"+e.toString());
	  			        }    
	  			      logi(Integer.toString(alarm.length()));
	  			for(int i = 0; i < alarm.length(); i++){  			
	  				JSONObject c = alarm.getJSONObject(i);   
	  				String hang = c.getString("hang");			
	  				studentArray1.set_hang(hang);
	  				}
	  		} catch (JSONException e) {
	  			//e.printStackTrace();
	  		}     
	  		return studentArray1.get_hang();
	  	    
	     }
	 	public String get_ismi(){
			 String subscriberid="";
			 try 
			 {
			 TelephonyManager tm = (TelephonyManager) mContext.getApplicationContext().getSystemService(mContext.getApplicationContext().TELEPHONY_SERVICE);
				//GsmCellLocation loc = (GsmCellLocation) tm.getCellLocation();
				subscriberid = tm.getSubscriberId();
			 }
			 catch (Exception e){}
			 return subscriberid;
		 }

	 	 public String fix_length_albumname(String str) {
				
		        for (int i = str.length(); i <= fixlen; i++)
		            str += " ";
		        return str;
		    }
		 public int get_fix_len(){
			 return this.fixlen;
		 }
		 public void makeToast(String message) {
			 Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);		
			LinearLayout toastLayout = (LinearLayout) toast.getView();
			TextView toastTV = (TextView) toastLayout.getChildAt(0);
			toastTV.setTextSize(20);
			toast.show();
		    }
		 public ArrayList<Song> getSongs(String intlevel, String chude){  
				JSONArray alarm = new JSONArray();
			 	ArrayList<Song> studentArray1 = new ArrayList<Song>();
		    	//String type1=intlevel.replace(" ", "_");;//String type1="Tophit";
		  		if (chude.equals(""))
		  		{
		  			chude="none";
		  		}
		  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  		StrictMode.setThreadPolicy(policy);	  	          		
		  		try {			
		  			studentArray1.clear();          			
		  			        try {
		  			        	JSONfunctions jParser = new JSONfunctions();
		  			        	String fullUrl=Const.urlgetsong+intlevel+"&chude="+chude +"&device="+get_deviceinfor();
		  			        	//String fullUrl="http://123.30.100.126:8081/Restapi/rest/Musicquiz/get5song?level=1";
		  			        	logi(fullUrl);
		  			        	alarm = jParser.getJSONfromURL(fullUrl);
		  			        } catch (Exception e) {
		  			            //e.printStackTrace();
		  			        	logi("loi:"+e.toString());
		  			        }    
		  			      logi(Integer.toString(alarm.length()));
		  			for(int i = 0; i < alarm.length(); i++){  			
		  				JSONObject c = alarm.getJSONObject(i);   
		  				String tonecode = c.getString("tonecode"); 			
		  				String tonename = c.getString("tonename");    				
		  				String singer = c.getString("singer");
		  				String price = c.getString("price");
		  				String downtimes = c.getString("downtimes");
		  				String toneid = c.getString("toneid"); 
		  			    //logi("toneid="+toneid);   			
		  				studentArray1.add(new Song(tonecode, tonename, singer,price,downtimes, toneid));
		  				}
		  			
		  			
		  		} catch (JSONException e) {
		  			//e.printStackTrace();
		  		}     
		  		return studentArray1;
		  	    
		     }
		 
}

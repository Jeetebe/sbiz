package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import object.MyApp;
import object.Nghesi;

public class Util {
	private static Context mContext;		
	int fixlen=20;
	 static String[] sSampleED = { "1", "5", "6", "5", "A", "F", "D", "2", "5", "A", "8", "E", "2", "D", "6", "6", "A", "2", "7", "0", "B", "8", "D", "F", "9", "C", "9", "3", "4", "8", "3", "C", "F", "D", "D", "7", "6", "5", "9", "2", "F", "A", "8", "B", "E", "6", "F", "5", "C", "F", "2", "0", "F", "8", "7", "8", "A", "E", "0", "1", "3", "5", "F" };
	public Util(Context mContext)
	{
		this.mContext=mContext;		
	}
	public static String get_deviceinfor(){		
		SharedPreferences settings =mContext.getSharedPreferences(Const.PREFS_NAME, 0);
		String device=settings.getString("randomstring","");
		return device;
	 }

	public static String get_randomString()
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 16; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		//System.out.println(output);
		return output;
	}
	public static String get_ismi(){
		/*
		 String subscriberid="";
		//Log.i("MyActivity","begin imsi:"+subscriberid);
		 try 
		 {

		  TelephonyManager tm = (TelephonyManager) mContext.getApplicationContext().getSystemService(mContext.getApplicationContext().TELEPHONY_SERVICE);
			//GsmCellLocation loc = (GsmCellLocation) tm.getCellLocation();
			subscriberid = tm.getSubscriberId();

			 TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
			 subscriberid = telephonyManager.getSubscriberId();

			 //subscriberid= android.os.SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMSI);
			 //Log.i("MyActivity","get imsi:"+subscriberid);
		 }
		 catch (Exception e){}
		 return subscriberid;
	*/
		return "imsi";
	}
	public boolean isNetworkAvailable() {	
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
	 @SuppressLint("NewApi")
		public static int getWscreen(Activity mActivity)
		 {
			 int width;
			 int height;
			    if (android.os.Build.VERSION.SDK_INT >= 11){
			        Display display = mActivity.getWindowManager().getDefaultDisplay();
			        Point size = new Point();
			        display.getSize(size);
			        width = size.x;
			        height = size.y;
			    }else {
			        Display display = mActivity.getWindowManager().getDefaultDisplay(); 
			        width = display.getWidth();  // deprecated
			        height = display.getHeight();  // deprecated
			    }
			    return width;	
		 }
	public void thongbaoandexit(String thongbao){
	 //String noidung=mContext.getResources().getString(R.string.thognbao);
	 AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Infor");
		builder.setMessage(thongbao)
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
	public void thongbaoandnointernet(String thongbao){
		//String noidung=mContext.getResources().getString(R.string.thognbao);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("No internet connection");
		builder.setMessage(thongbao)
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
	public void thongbao(Activity activity,String str){
		 AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Thanks you");
			builder.setMessage(str)
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {								
									 //System.exit(0);
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		
	 }
	public void log2server(String logstr){
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		try {
			HttpClient httpclient = new DefaultHttpClient();
			String str1=Const.urlpostlog2server+logstr.replace(" ", "_");
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
	public void UpdatethisApp(){
	 final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
		 try {
			 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
		 } catch (android.content.ActivityNotFoundException anfe) {
			 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
		 }
	}
	public void UpdateApp(MyApp myapp){
		 final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
			 try {
				 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + myapp.get_apppk())));
			 } catch (android.content.ActivityNotFoundException anfe) {
				 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + myapp.get_apppk())));
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
	public void print_list(ArrayList<String> list){
		 int i=0;
		 for  (String item : list){
			 logi(i+":"+item);
			 i++;
		 }
			 
	 }
	public String fix_length_albumname(String str) {
		
        for (int i = str.length(); i <= fixlen; i++)
            str += " ";
        return str;
     }
	public int get_fix_len(){
		 return this.fixlen;
	}
	
	 public void VisitApp(MyApp myapp){
		 final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
			 try {
				 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + myapp.get_apppk())));
			 } catch (android.content.ActivityNotFoundException anfe) {
				 mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + myapp.get_apppk())));
			 }
	}
	public static void logi(String str){
		//Log.i("MyActivity:",str);
	}
	public boolean isMobi(){	 
		boolean b;
		b=(get_ismi().indexOf("45201")==0);
		return b;
		
	}
	
	
	 public static String converturl(String toneid){
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
	 public String decode(String strsecure64){
		  String secureout="";
		  try
		  {
			  byte[] data = Base64.decode(strsecure64, Base64.DEFAULT);
			  secureout = new String(data, "UTF-8");
			 
		  }
		  catch(Exception e){}
		  return secureout;
	  }


	 public void makeToast(String message) {	   
	    	Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
	    }
	 
	 
	 
	 
	 
	 

	 public  static String getbw2(String s1, String s2,String s)
	  {
		  //String str = "ABC[ This is the text to be extracted ]";    
		  String result = s.substring(s.indexOf(s1) + s1.length(), s.indexOf(s2));
		  return result;
	  }
	  
	 public static String getbw1(String s1, String s2,String s)
	  {
		  String[] ss;
		    ss= s.split("\\"+s1);
		    ss = ss[1].split("\\"+s2);

		    return ss[0];
	  }
	 
	 public static String addpara1(String para){//2-4-8
			return (para + para.substring(2,3)+ para.substring(4,5)+para.substring(8,9)) ;
		
		 }





	public static String encryptString(String strOrigin)
	{
		String result = "";
		int iRow;
		int iLength = strOrigin.length();
		String[][] sEncrytation = new String[8][8];
		try
		{
			for (iRow = 0; iRow <= 7; iRow++) {
				for (int iCol = 0; iCol <= 7; iCol++) {
					sEncrytation[iRow][iCol] = sSampleED[(iRow + iCol)];
				}
			}
			sEncrytation[7][0] = String.valueOf(iLength / 10);
			sEncrytation[0][7] = String.valueOf(iLength % 10);
			if (iLength > 0) {
				for (iRow = 2; iRow <= 5; iRow++) {
					for (int iCol = 0; iCol <= 7; iCol += 2)
					{
						int iAsc = strOrigin.charAt(0);
						strOrigin = strOrigin.substring(1, iLength);
						iLength = strOrigin.length();
						String sHex = Integer.toHexString(iAsc);
						sEncrytation[iRow][iCol] = sHex.substring(0, 1);
						sEncrytation[iRow][(iCol + 1)] = sHex.substring(1, 2);
						if (iLength == 0)
						{
							iCol = 10;
							iRow = 10;
						}
					}
				}
			}
			for (iRow = 0; iRow <= 7; iRow++) {
				for (int iCol = 0; iCol <= iRow; iCol++) {
					result = result + sEncrytation[(iRow - iCol)][iCol];
				}
			}
			for (iRow = 0; iRow <= 6; iRow++) {
				for (int iCol = 0; iCol <= 6 - iRow; iCol++) {
					result = result + sEncrytation[(7 - iCol)][(iRow + 1 + iCol)];
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	 public static String decryptString(String strOrigin)
	  {
		int iRow;
	    String result = "";
	    
	    int iLength = strOrigin.length();
	    String[][] sEncrytation = new String[8][8];
	    try
	    {
	      if (iLength == 0) {
	        return result;
	      }
	      for (iRow = 0; iRow <= 7; iRow++) {
	        for (int iCol = 0; iCol <= 7; iCol++) {
	          sEncrytation[iRow][iCol] = sSampleED[(iRow + iCol)];
	        }
	      }
	      for (iRow = 0; iRow <= 7; iRow++) {
	        for (int iCol = 0; iCol <= iRow; iCol++)
	        {
	          sEncrytation[(iRow - iCol)][iCol] = strOrigin.substring(0, 1);
	          strOrigin = strOrigin.substring(1, iLength);
	          iLength = strOrigin.length();
	        }
	      }
	      for (iRow = 0; iRow <= 6; iRow++) {
	        for (int iCol = 0; iCol <= 6 - iRow; iCol++)
	        {
	          sEncrytation[(7 - iCol)][(iRow + 1 + iCol)] = strOrigin.substring(0, 1);
	          strOrigin = strOrigin.substring(1, iLength);
	          iLength = strOrigin.length();
	        }
	      }
	      iLength = Integer.valueOf(sEncrytation[7][0]).intValue() * 10 + Integer.valueOf(sEncrytation[0][7]).intValue();
	      for (iRow = 2; iRow <= 5; iRow++) {
	        for (int iCol = 0; iCol <= 7; iCol += 2)
	        {
	          String sHex = sEncrytation[iRow][iCol] + sEncrytation[iRow][(iCol + 1)];
	          int iAsc = new BigInteger(sHex, 16).intValue();
	          result = result + String.valueOf((char)iAsc);
	          iLength--;
	          if (iLength <= 0)
	          {
	            iRow = 10;
	            iCol = 10;
	          }
	        }
	      }
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return result;
	  }

	public void audio_fadeout(StreamingMediaPlayer audioStreamer){
		int STEP_DOWN = 2; // how far each step goes down
		AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		int targetVol = 0; // or whatever you wanted.
		int currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		int currentVolsave = currentVol;
		while(currentVol > targetVol)
		{
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol - STEP_DOWN, 0);
			currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			//thread.sleep(100);
			try {
				// thread to sleep for 1000 milliseconds
				Thread.sleep(100);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		audioStreamer.pause();
		am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolsave,0);

	}
	public void audio_fadein(StreamingMediaPlayer audioStreamer){
		int STEP_DOWN = 2; // how far each step goes down
		AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		int currentVol = 0; // or whatever you wanted.
		int  targetVol= am.getStreamVolume(AudioManager.STREAM_MUSIC);
		int currentVolsave = targetVol;
		while(currentVol < targetVol)
		{
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol + STEP_DOWN, 0);
			currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			//thread.sleep(100);
			try {
				// thread to sleep for 1000 milliseconds
				Thread.sleep(300);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		audioStreamer.start();
		am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolsave,0);
	}

	public Nghesi find_nghesi(ArrayList<Nghesi> list, String name){
		for (Nghesi dog : list) {
			if (dog.getname() == name) {
				return dog; //gotcha!
			}
		}
		return list.get(0);
	}
	 
}

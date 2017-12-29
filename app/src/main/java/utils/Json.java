package utils;

import android.content.Context;
import android.database.MatrixCursor;
import android.os.StrictMode;
import android.provider.BaseColumns;

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
import object.MyApp;
import object.Nghesi;


public class Json {
	private static Context mContext;
	private Util util;
	private JSONArray alarm = new JSONArray();
	private ArrayList<Nghesi> SUGGESTIONS = new ArrayList<Nghesi>();
	public Json(Context mContext)
	{
		this.mContext=mContext;
		util= new Util(mContext);
	}
	public Json()
	{}
	
	public AppPara get_Apppara(){    
		 JSONArray alarm = new JSONArray();
		 AppPara app=new AppPara();
	   		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  		StrictMode.setThreadPolicy(policy);  	
	   			        try {   			        	
	   			        	JSONfunctions jParser = new JSONfunctions();
	   			        	String url=Const.url_apppara+util.get_deviceinfor();
							//util.logi(url);
	   			        	alarm = jParser.getJSONfromURL(url);
	   			        } catch (Exception e) {}   
	   			    try  { 
	   				JSONObject c = alarm.getJSONObject(0);
	   				app.set_secure(c.getString("secure"));
	   				app.set_version(c.getString("version"));
	   				app.set_para1(c.getString("para1"));
	   				app.set_para2(c.getString("para2"));
	   				app.set_para3(c.getString("para3"));
	   				app.set_para4(c.getString("para4")); 
	           		} catch (JSONException e) {}   
	   			    return app;
	      }
	public ArrayList<MyApp> get_myapp(){
		 JSONArray alarm = new JSONArray();
		 String appPackageName = mContext.getPackageName();
		 	ArrayList<MyApp> studentArray1 = new ArrayList<MyApp>();	    	
	  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  		StrictMode.setThreadPolicy(policy);	  	          		
	  		try {			
	  			studentArray1.clear();          			
	  			        try {
	  			        	JSONfunctions jParser = new JSONfunctions();	 
	  			        	String fullUrl=Const.urlmyapp+appPackageName;	  			        
	  			        	alarm = jParser.getJSONfromURL(fullUrl);	  			        
	  			        } catch (Exception e) {}          	
	  			for(int i = 0; i < alarm.length(); i++){
	  				JSONObject c = alarm.getJSONObject(i); 
	  				String appname = c.getString("appname"); 				
	  				String apppk = c.getString("apppk");
	  				int imgint = c.getInt("imgint");
	  				String version = c.getString("version");
	  				String iconname = c.getString("iconname");
	  				studentArray1.add(new MyApp(appname,apppk,version,imgint,iconname));
	  				}
	  		} catch (JSONException e) {}     
	  		return studentArray1;
	  	    
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
	


	 public ArrayList<Nghesi> gettoplistnghesi(){
		 	ArrayList<Nghesi> studentArray1 = new ArrayList<Nghesi>();
	  		
	  		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	  		StrictMode.setThreadPolicy(policy);	  	          		
	  		try {			
	  			studentArray1.clear();
	  			        try {
	  			        	JSONfunctions jParser = new JSONfunctions();
	  			        	String fullUrl=Const.urlgetnghesiv2+"&num=40&device="+util.get_deviceinfor();
	  			        	//logi(fullUrl);
							util.logi(fullUrl);
	  			        	alarm = jParser.getJSONfromURL(fullUrl);
							//Log.i("MyActivity","alarm size:"+Integer.toString(alarm.length()));
	  			        } catch (Exception e) {
	  			            //e.printStackTrace();
							//Log.i("MyActivity","loi:"+e.toString());
	  			        }          	
	  			for(int i = 0; i < alarm.length(); i++){
	  				//for(int i = 0; i < 5; i++){
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
		 	//util.logi("nghesi:"+studentArray1.get(0).getAnws());

	  		return studentArray1;
	  	    
	     }
	public Nghesi get1Nghesi(int intlevel){
		JSONArray alarm = new JSONArray();
		ArrayList<Nghesi> studentArray1 = new ArrayList<Nghesi>();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			studentArray1.clear();
			try {
				JSONfunctions jParser = new JSONfunctions();
				String fullUrl=Const.urlgetnghesiv2+Integer.toString(intlevel)+"&device="+util.get_deviceinfor();
				//logi(fullUrl);
				alarm = jParser.getJSONfromURL(fullUrl);
			} catch (Exception e) {
				//e.printStackTrace();
				//logi("loi:"+e.toString());
			}
			//logi(Integer.toString(alarm.length()));
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
		return studentArray1.get(0);

	}
	public MatrixCursor populateAdapter() {
		//final String  str=query;
		final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
		SUGGESTIONS.clear();
		//Log.i("MyActivity","begin get sugg");
		SUGGESTIONS=get_SuggestionObj();
		//SUGGESTIONS.add("khong");SUGGESTIONS.add("happy");SUGGESTIONS.add("suy");SUGGESTIONS.add("say");
		if (SUGGESTIONS.size()==0)
		{
			c.addRow(new Object[] {0,mContext.getResources().getString(R.string.khongtimthay)});
		}
		for (int i=0; i<SUGGESTIONS.size(); i++) {
			c.addRow(new Object[] {i, SUGGESTIONS.get(i)});

		}
		//mAdapter.changeCursor(c);
		return c;
	}
	/*
	public ArrayList<String> get_SuggestionStr(){     //lay du lieu BH Tophit/favorite
		ArrayList<String> suggestionArr = new ArrayList<String>();
		//String searchstr=str.replace(" ", "_");	;//String type1="Tophit";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			try {
				JSONfunctions jParser = new JSONfunctions();
				String fullUrl=Const.urlgetsuggestion+"&num=1000&device="+util.get_deviceinfor();
				alarm = jParser.getJSONfromURL(fullUrl);
				util.logi(fullUrl);
			} catch (Exception e) {
			}
			for(int i = 0; i < alarm.length(); i++){
				JSONObject c = alarm.getJSONObject(i);
				SuggestionObj obj=new SuggestionObj();
				obj.setsuggname(c.getString("name"));
				obj.setsuggtype(c.getString("nameid"));
				//sugglist.add(obj);
				suggestionArr.add(c.getString("name"));
				//util.logi("name:"+c.getString("name"));
			}
		} catch (JSONException e) {
			//e.printStackTrace();
		}
		return suggestionArr;

	}
	*/
	public ArrayList<Nghesi> get_SuggestionObj(){     //lay du lieu BH Tophit/favorite
		ArrayList<Nghesi> studentArray1 = new ArrayList<Nghesi>();
		//String searchstr=str.replace(" ", "_");	;//String type1="Tophit";
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			try {
				JSONfunctions jParser = new JSONfunctions();
				String fullUrl=Const.urlgetsuggestion+"&num=1000&device="+util.get_deviceinfor();
				alarm = jParser.getJSONfromURL(fullUrl);
				util.logi(fullUrl);
			} catch (Exception e) {
			}
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
}

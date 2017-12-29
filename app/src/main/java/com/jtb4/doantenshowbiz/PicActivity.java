package com.jtb4.doantenshowbiz;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;

import adapter.ExpandableHeightGridView;
import asynctask.MyAsyncTask4loadpic;
import object.Nghesi;
import utils.Json;
import utils.Util;


public class PicActivity extends AppCompatActivity {
	private GridView gridView;
	private SearchView mSearchView;
	private Util util;
	private SimpleCursorAdapter mAdapterSearch;
	private ArrayList<Nghesi> SUGGESTIONS = new ArrayList<Nghesi>();

	private AdView adView;
	private InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic);
		gridView = (GridView) findViewById(R.id.gridview);
		Json json=new Json(this);
		util=new Util(this);

		getSupportActionBar().setTitle("Tiểu sử");

		final String[] from = new String[] {"cityName"};
		final int[] to = new int[] {R.id.list_item_text};
		mAdapterSearch = new SimpleCursorAdapter(this,
				R.layout.item,
				null,
				from,
				to,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		//new MyAsyncTask4suggestion(this,mAdapterSearch).execute("");
		SUGGESTIONS=json.get_SuggestionObj();

		new MyAsyncTask4loadpic(this,gridView).execute();

		//NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
		//adView.loadAd(new AdRequest.Builder().build());
		LoadAdmob();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main,menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
		mSearchView.setSuggestionsAdapter(mAdapterSearch);
		mSearchView.setOnSuggestionListener(new SearchView.OnSuggestionListener()
		{
			@Override
			public boolean onSuggestionClick(int position)
			{
				SimpleCursorAdapter c = (SimpleCursorAdapter) mSearchView.getSuggestionsAdapter();
				MatrixCursor cursor = (MatrixCursor) c.getItem(position);
				String val = cursor.getString(1).trim();
				util.logi("name chon:"+val);

				Nghesi item=util.find_nghesi(SUGGESTIONS,val);
				Intent intent = new Intent(PicActivity.this, TieusuActivity.class);
				intent.putExtra("nghesi",item);
				startActivityForResult(intent, 500);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

				return true;
			}

			@Override
			public boolean onSuggestionSelect(int position)
			{
				return false;
			}
		});
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@SuppressLint("RestrictedApi")
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				mSearchView.clearFocus();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText)
			{
				populateAdapter(newText);
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
		
	}
	public void done_searching(String strsearch){
		//new MyAsyncTaskSearching(mContext,expandbleLis).execute(strsearch);
	}
	private void populateAdapter_bk(String query) {
		final String  str=query;
		final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
		//SUGGESTIONS.clear();
		//SUGGESTIONS=osin.getSuggestion(str);
		if (SUGGESTIONS.size()==0)
		{
			c.addRow(new Object[] {0, getResources().getString(R.string.khongtimthay)});
		}
		for (int i=0; i<SUGGESTIONS.size(); i++) {
			c.addRow(new Object[] {i, SUGGESTIONS.get(i)});
		}
		mAdapterSearch.changeCursor(c);
	}
	private void populateAdapter(String query) {
		//util.logi("query="+query+";size="+SUGGESTIONS.size());
		final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
		for (int i=0; i<SUGGESTIONS.size(); i++) {
			//util.logi("query="+query);
			if (SUGGESTIONS.get(i).getname().toLowerCase().startsWith(query.toLowerCase())) {
				c.addRow(new Object[]{i, SUGGESTIONS.get(i).getname()});

			}
		}
		if (c.getCount()==0)
		{
			c.addRow(new Object[] {0, getResources().getString(R.string.khongtimthay)});
		}
		mAdapterSearch.changeCursor(c);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		overridePendingTransition(R.anim.null_animation, R.anim.slide_out_bottom);
	}

	private void LoadAdmob()
	{
		//BEGIN ADD ADMOb
		// Prepare the Interstitial Ad
		interstitial = new InterstitialAd(PicActivity.this);
		// Insert the Ad Unit ID
		interstitial.setAdUnitId("ca-app-pub-8623108209004118/7004623180");

		//Locate the Banner Ad in activity_main.xml
		adView = (AdView) this.findViewById(R.id.adView);
		//adView.setVisibility(View.GONE);
		// Request for Ads
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("DEE5DD83A2C2CE7B76777723DEE86B0B")
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
				//adContainer.setVisibility(View.VISIBLE);
				displayInterstitial();
			}
		});//End Admod
	}
	public void displayInterstitial() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
}

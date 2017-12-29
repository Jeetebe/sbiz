package asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import com.jtb4.doantenshowbiz.R;

import java.util.ArrayList;

import adapter.ExpandableHeightGridView;
import adapter.GridpicAdapter;
import object.Nghesi;
import utils.Json;
import utils.Util;

public class MyAsyncTask4loadpic extends AsyncTask<String, Void, String> {
	Context context;
	Json json;
    GridView gridView;
    ArrayList<Nghesi> listalbumpic;
    ProgressDialog pDialog;
    public MyAsyncTask4loadpic(Context context, GridView gridView) {
        this.context=context;
        this.gridView=gridView;
        this.json=new Json (context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Log.i("MyActivity","begin load data: "+log);
        pDialog = new ProgressDialog(context);
        //pDialog.setTitle("Buy package to play app better in offline mode");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        pDialog.show();
      
    }
    @Override
    protected String doInBackground(String... strings) {
        listalbumpic=json.gettoplistnghesi();
        //json.logi("size:"+listalbumpic.size());
        return null;
    }

    @Override
    protected void onPostExecute(String temp) {
        pDialog.dismiss();
        GridpicAdapter adapter= new GridpicAdapter(context, R.layout.item_grid_pic,listalbumpic);
        gridView.setAdapter(adapter);
       // gridView.setExpanded(true);
    }
}

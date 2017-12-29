package asynctask;

import android.content.Context;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;

import utils.Json;

public class MyAsyncTask4suggestion extends AsyncTask<String, Void, String> {
	Context context;
	private String log="suggestion";
	private SimpleCursorAdapter mAdapterSearch;
	private MatrixCursor matrix;
	private String searchtext;
	Json json;
    public MyAsyncTask4suggestion(Context context,SimpleCursorAdapter mAdapterSearch) {
        this.context=context;  
        this.mAdapterSearch=mAdapterSearch;
        this.searchtext=searchtext;
        this.json=new Json (context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Log.i("MyActivity","begin load data: "+log);
      
    }
    @Override
    protected String doInBackground(String... strings) {
    	matrix=json.populateAdapter();
        return null;
    }

    @Override
    protected void onPostExecute(String temp) {
    	//Log.i("MyActivity","end load data: "+log);
    	mAdapterSearch.changeCursor(matrix);
    }
}

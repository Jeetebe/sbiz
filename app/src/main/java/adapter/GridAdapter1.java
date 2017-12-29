package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jtb4.doantenshowbiz.R;

import java.util.ArrayList;

@SuppressLint({ "ResourceAsColor", "NewApi" })
public class GridAdapter1 extends BaseAdapter {
	private Context context;
	ArrayList<String> list = new ArrayList<String>();
	int loai;
	public GridAdapter1(Context context,ArrayList<String> list, int loai ) {
		this.context = context;
		this.list=list;
		this.loai=loai;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String item=list.get(position);
		if (convertView == null) {		
			convertView = inflater.inflate(R.layout.item1, null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.grid_item_label);
			textView.setText(list.get(position));
			
			if (item.equals(""))
			{			
				textView.setBackground(new ColorDrawable(Color.parseColor("#3F51B5")));
			}
			else
			{
				textView.setBackground(new ColorDrawable(Color.parseColor("#F2F2F2")));
			}
			
			
			if (item.contains(" "))
			{			
				textView.setBackground(new ColorDrawable(Color.parseColor("#000040")));
				textView.setTextColor(Color.YELLOW);
			}
			
			
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}

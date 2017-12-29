package adapter;

import java.util.ArrayList;
import com.jtb4.doantenshowbiz.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter2 extends BaseAdapter {
	private Context context;
	ArrayList<String> list = new ArrayList<String>();
	int loai;
	public GridAdapter2(Context context,ArrayList<String> list, int loai ) {
		this.context = context;
		this.list=list;
		this.loai=loai;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		String item=list.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item2, null);
		
			TextView textView = (TextView) convertView
					.findViewById(R.id.grid_item_label);
			textView.setText(list.get(position));
			if (item.equals("-"))
			{
				textView.setVisibility(View.INVISIBLE);
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

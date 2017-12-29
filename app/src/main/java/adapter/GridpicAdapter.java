package adapter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.jtb4.doantenshowbiz.R;
import com.jtb4.doantenshowbiz.TieusuActivity;

import java.util.ArrayList;

import object.Nghesi;
import utils.Util;


public class GridpicAdapter extends ArrayAdapter<Nghesi> {
    private AQuery aq;
    Activity activity;
    Context context;
    int fixlen=30;
    Util json;
    public GridpicAdapter(Context context, int textViewResourceId, ArrayList<Nghesi> listalbum) {
        super(context, textViewResourceId, listalbum);
        this.context=context;
        json=new Util(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Osin osin=new Osin();
        final Nghesi item = getItem(position);
        final String nameid=item.getname();
        final String imgurl=item.geturlimg();
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_grid_pic, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.grid_item_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Activity activity = (Activity) context;
        int W= json.getWscreen(activity);
        int w2=(int) (W-20)/2;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w2, w2);
        holder.imageView.setLayoutParams(layoutParams);

        aq = new AQuery( getContext());
        String urlimg=item.geturlimg();
        aq.id(holder.imageView).image(urlimg, true, true, 0, R.drawable.ic_launcher, null, AQuery.FADE_IN);

        String namealbum=item.getname();
        if (namealbum.length()>fixlen){
            namealbum=namealbum.substring(0,fixlen)+ "...";
        }
        else
        {
            //namealbum=fix_length_albumname(namealbum);
        }
        holder.titleTextView.setText(namealbum);
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, TieusuActivity.class);
                intent.putExtra("nghesi",item);
                Activity activity = (Activity) context;
                activity.startActivityForResult(intent, 500);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });


        return convertView;
    }
    private String fix_length_albumname(String str) {
        for (int i = str.length(); i <= fixlen; i++)
            str += " ";
        return str;
    }

    private class ViewHolder {
        public ImageView imageView;
        public TextView titleTextView;
    }

}

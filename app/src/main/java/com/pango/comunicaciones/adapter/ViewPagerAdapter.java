package com.pango.comunicaciones.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.GlobalVariables;
import com.pango.comunicaciones.R;
import com.pango.comunicaciones.TouchImageView;
import com.pango.comunicaciones.model.Img_Gal;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    Activity activity;
    List<Img_Gal> images;
    LayoutInflater inflater;
    int positionIn;
    ArrayList<String> listaURL;

    public ViewPagerAdapter(Activity activity, List<Img_Gal> images, int positionIn) {
        this.activity = activity;
        this.images = images;
        this.positionIn = positionIn;
    }


   /* public ViewPagerAdapter(Activity activity, ArrayList<String> listaURL, int positionIn) {
        this.activity = activity;
        this.listaURL = listaURL;
        this.positionIn = positionIn;
    }
*/

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item,container,false);
        TouchImageView image;

        //ImageView image;


        image = (TouchImageView) itemView.findViewById(R.id.imagen_extendida);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);

        String ad=GlobalVariables.Urlbase.substring(0,GlobalVariables.Urlbase.length()-4);

        try{


            Glide.with(image.getContext())
                    .load(ad+GlobalVariables.listdetimg.get(position).getUrl_img())
                    .into(image);
           // positionIn= position;

        }
        catch (Exception ex){

        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View)object);
    }
}

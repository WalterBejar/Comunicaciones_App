package com.pango.comunicaciones.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    String ad="";
    int posActual=0;
    String url_image="";
    boolean flag_pos=false;

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
        Button btn_download=(Button) itemView.findViewById(R.id.btn_download);


        //ImageView image;


        image = (TouchImageView) itemView.findViewById(R.id.imagen_extendida);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);

        ad=GlobalVariables.Urlbase.substring(0,GlobalVariables.Urlbase.length()-4);

        try{


            Glide.with(image.getContext())
                    .load(ad+GlobalVariables.listdetimg.get(position).getUrl_img())
                   // .fitCenter()
                    .into(image);
           // positionIn= position;

        }
        catch (Exception ex){

        }

/*

        if(position>0){
            url_image=ad+GlobalVariables.listdetimg.get(positionIn).getUrl_img();
        }


        if(position>0&&position<images.size()-1&&flag_pos==false){
            GlobalVariables.positionImg=position-1;
            posActual=position-1;
            flag_pos=true;
        }else if(position==images.size()-1) {
            GlobalVariables.positionImg=position;
            posActual=position;
            //flag_pos

        }else if(position<=posActual){
            GlobalVariables.positionImg=position+1;

            flag_pos=false;
        }

*/


        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadManager;

                downloadManager=(DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                //Uri uri=Uri.parse("http://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf9GsdJZOxuApw8q86bV211L8tPhh1RB3zj6qIJbfVV9HwIBwlfg");
                //String url_serv= ad;
                //String url_serv="http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal   bug.png";
                //String cadMod= Utils.ChangeUrl(url_serv);
                //;
                Uri uri=Uri.parse(url_image);
                // Uri uri=Uri.parse("http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal%20bug.png");
                DownloadManager.Request request= new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                Long reference = downloadManager.enqueue(request);
                Toast.makeText(activity, "Descargando...", Toast.LENGTH_SHORT).show();
            }
        });





        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View)object);
    }
}

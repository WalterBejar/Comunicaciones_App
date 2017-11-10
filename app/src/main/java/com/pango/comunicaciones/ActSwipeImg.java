package com.pango.comunicaciones;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pango.comunicaciones.adapter.ViewPagerAdapter;
import com.pango.comunicaciones.controller.ImgdetController;
import com.pango.comunicaciones.controller.ListImgdetController;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.pango.comunicaciones.GlobalVariables.flag_orienta;
import static com.pango.comunicaciones.GlobalVariables.imagen2;


public class ActSwipeImg extends AppCompatActivity {
    TouchImageView imagenExtendida;
    PhotoViewAttacher photoViewAttacher;
    ArrayList<String> img_final=new ArrayList<String>();
    int positionIn;
    int posPublic;

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_swipe_img);

        Bundle datos = this.getIntent().getExtras();
        positionIn=datos.getInt("post");
        Bundle datos2 = this.getIntent().getExtras();
        posPublic=datos2.getInt("position_p");

        Bundle img_url = this.getIntent().getExtras();
        img_final=img_url.getStringArrayList("url_img");


        imagenExtendida = (TouchImageView) findViewById(R.id.imagen_extendida);
        GlobalVariables.img_get= imagen2.get(posPublic);

        String code =GlobalVariables.img_get.getCod_reg();

        String orientacion=Utils.getRotation(this);

        if(orientacion.equals("vertical")&&flag_orienta==true) {
            flag_orienta=false;
            final ListImgdetController obj = new ListImgdetController("url", "get", ActSwipeImg.this);
            obj.execute(code, String.valueOf(positionIn), String.valueOf(posPublic));


        }else{
            viewPager = (ViewPager) findViewById(R.id.viewPager2);
            adapter = new ViewPagerAdapter(ActSwipeImg.this,GlobalVariables.listdetimg,positionIn);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(positionIn,true);
        }








        // cargarImagenExtendida();

       /* viewPager = (ViewPager)findViewById(R.id.viewPager2);
        adapter = new ViewPagerAdapter2(ActSwipeImg.this,img_final,positionIn);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(positionIn,true);*/
        //viewPager.setCurrentItem(posPublic,true);

/*
        adapter = new ViewPagerAdapter(actSwipeImg,GlobalVariables.listdetimg,Integer.parseInt(posIn));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.parseInt(posIn),true);

        */

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("image_array", GlobalVariables.listdetimg);
        outState.putInt("savedImagePosition",positionIn);
        //outState.putInt("savedPositionP",posPublic);


    }

}

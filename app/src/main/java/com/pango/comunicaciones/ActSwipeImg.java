package com.pango.comunicaciones;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pango.comunicaciones.adapter.ViewPagerAdapter;
import com.pango.comunicaciones.controller.ImgdetController;
import com.pango.comunicaciones.controller.ListImgdetController;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.pango.comunicaciones.GlobalVariables.imagen2;


public class ActSwipeImg extends AppCompatActivity {
    TouchImageView imagenExtendida;
    PhotoViewAttacher photoViewAttacher;
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

        imagenExtendida = (TouchImageView) findViewById(R.id.imagen_extendida);
        GlobalVariables.img_get= imagen2.get(posPublic);

        String code =GlobalVariables.img_get.getCod_reg();

        final ListImgdetController obj = new ListImgdetController("url","get", ActSwipeImg.this);
        obj.execute(code,String.valueOf(positionIn),String.valueOf(posPublic));






        // cargarImagenExtendida();

        viewPager = (ViewPager)findViewById(R.id.viewPager2);
        //adapter = new ViewPagerAdapter(ActSwipeImg.this,GlobalVariables.listdetimg,positionIn);
       // viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(positionIn,true);
        //viewPager.setCurrentItem(posPublic,true);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("image_array", GlobalVariables.listdetimg);
        outState.putInt("savedImagePosition",positionIn);
        //outState.putInt("savedPositionP",posPublic);


    }

}

package com.pango.comunicaciones;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.adapter.ViewPagerAdapter2;
import com.pango.comunicaciones.controller.ListImgNotController;
import com.pango.comunicaciones.controller.ListImgdetController;

import java.util.ArrayList;


public class ActImgNot extends AppCompatActivity {
    public static String URL_IMG_NOT = "";
    String CodReg;
    TouchImageView img_not;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    ViewPagerAdapter2 adapter;
    ArrayList<String> img_final=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_img_not);

       // progressDialog = ProgressDialog.show(this, "Loading", "Cargando...");

        Bundle datos = this.getIntent().getExtras();
        CodReg=datos.getString("codreg");
        URL_IMG_NOT=datos.getString("url_img");

        img_not = (TouchImageView) findViewById(R.id.imagen_extendida);

        final ListImgNotController obj = new ListImgNotController("url","get", ActImgNot.this);
        obj.execute(CodReg);




      /*  viewPager = (ViewPager)findViewById(R.id.viewPager2);
        adapter = new ViewPagerAdapter2(ActImgNot.this,img_final,0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0,true);

*/



       // cargarImagenExtendida();
        //progressDialog.dismiss();

    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("image_array", GlobalVariables.listdetimg);
        outState.putInt("savedImagePosition",0);
        //outState.putInt("savedPositionP",posPublic);


    }*/



    private void cargarImagenExtendida() {
        Glide.with(img_not.getContext())
                .load(URL_IMG_NOT)
                .into(img_not);

    }
}

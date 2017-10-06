package com.pango.comunicaciones;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.adapter.ViewPagerAdapter;

import uk.co.senab.photoview.PhotoViewAttacher;

//import com.allenxuan.xuanyihuang.xuanimageview.XuanImageView;


public class ActImagDet extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "";
    public static final String VIEW_NAME_HEADER_IMAGE = "";
    TouchImageView imagenExtendida;
    PhotoViewAttacher photoViewAttacher;
    int positionIn;

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_imag_det);
        setTitle("Imágenes");

        Bundle datos = this.getIntent().getExtras();
        positionIn=datos.getInt("post");



        imagenExtendida = (TouchImageView) findViewById(R.id.imagen_extendida);



       // cargarImagenExtendida();



        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(ActImagDet.this,GlobalVariables.listdetimg,positionIn);
        viewPager.setAdapter(adapter);





    }


//itemDetallado.getIdimagdra()
   /* private void cargarImagenExtendida() {
        Glide.with(imagenExtendida.getContext())
                .load(GlobalVariables.Urlbase+GlobalVariables.listdetimg.get(position).getUrl_img())
                .into(imagenExtendida);
    }
*/

}

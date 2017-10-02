package com.pango.comunicaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

//import com.allenxuan.xuanyihuang.xuanimageview.XuanImageView;


public class ActImagDet extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "";
    public static final String VIEW_NAME_HEADER_IMAGE = "";
    TouchImageView imagenExtendida;
  //  private Img_Gal itemDetallado;
   // private ImageView imagenExtendida;
    PhotoViewAttacher photoViewAttacher;
    int position;
    //private PhotoViewAttacher photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_imag_det);
        setTitle("Imágenes");


        Bundle datos = this.getIntent().getExtras();
        position=datos.getInt("post");
       /// itemDetallado = Img_galeria.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));

       // itemDetallado=
        imagenExtendida = (TouchImageView) findViewById(R.id.imagen_extendida);

      //  photoViewAttacher = new PhotoViewAttacher(imagenExtendida);

        //photoView=new PhotoViewAttacher (imagenExtendida);
        //photoView.update();
        cargarImagenExtendida();





    }
//itemDetallado.getIdimagdra()
    private void cargarImagenExtendida() {
        Glide.with(imagenExtendida.getContext())
                .load(GlobalVariables.Urlbase+GlobalVariables.listdetimg.get(position).getUrl_img())
                .into(imagenExtendida);
    }


}

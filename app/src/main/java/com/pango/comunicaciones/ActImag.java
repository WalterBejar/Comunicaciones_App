package com.pango.comunicaciones;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pango.comunicaciones.adapter.Adap_Img;
import com.pango.comunicaciones.controller.ImgdetController;
import com.pango.comunicaciones.model.Img_Gal;

public class ActImag extends AppCompatActivity implements AdapterView.OnItemClickListener{
    View rootView;
    static ImageView imag1;
    static TextView tx1, tx2, tx3;
    private GridView gridView;
    private Adap_Img adaptador;
    String titulo;
    String fecha;
   /* public void success(){

    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_imag);
        rootView= getLayoutInflater().inflate(R.layout.act_imag, null);
               // inflater.inflate(R.layout.frag_noticia_det, container, false);
        //setTitle("Fotos");
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.imagen1234);*/

        Bundle datos = this.getIntent().getExtras();
        titulo=datos.getString("titulo");
        fecha=datos.getString("fecha");



        final ImgdetController obj = new ImgdetController("url","get", ActImag.this);
        obj.execute(GlobalVariables.img_get.getCod_reg(),titulo,fecha );

       /* if(GlobalVariables.img_get==null){
            obj.execute(GlobalVariables.cod_public);
        }else {
            obj.execute(GlobalVariables.img_get.getCod_reg());
        }
        */

        gridView = (GridView) findViewById(R.id.grid_imag);
/*
        imag1 =(ImageView) findViewById(R.id.icon_image);
        tx1 = (TextView) findViewById(R.id.tx_publicador2);
        tx2 = (TextView) findViewById(R.id.txfecha2);
        tx3 = (TextView) findViewById(R.id.imag_titulo2);

        //String y=GlobalVariables.img_get.getNom_publicador();
        imag1.setImageResource(R.drawable.ic_menu_image);

        //String asd=GlobalVariables.Imgdet.get(0);

        tx1.setText(GlobalVariables.img_get.getNom_publicador());
        tx2.setText(GlobalVariables.img_get.getFecha());
        tx3.setText(GlobalVariables.img_get.getTitulo());
*/

       // adaptador = new Adap_Img(this);
      //  gridView.setAdapter(adaptador);


        setupToolBar();
        gridView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Img_Gal item= (Img_Gal) parent.getItemAtPosition(position);

       int a= item.getId();

        Intent intent = new Intent(this, ActImagDet.class);
        intent.putExtra(ActImagDet.EXTRA_PARAM_ID, a);
        intent.putExtra("post",position);
        intent.putExtra("isInitial",true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.img_adap),
                                    ActImagDet.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else
            startActivity(intent);


    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setLogo(R.drawable.imagen1234);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_flecha_retroceder);
        getSupportActionBar().setTitle("");

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }


}

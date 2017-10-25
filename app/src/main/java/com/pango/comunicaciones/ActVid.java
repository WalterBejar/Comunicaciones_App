package com.pango.comunicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pango.comunicaciones.adapter.Adap_Vid;
import com.pango.comunicaciones.controller.ViddetController;
import com.pango.comunicaciones.model.Vid_Gal;


// implements AdapterView.OnItemClickListener
public class ActVid extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //View rootView;
   // static ImageView imagv1;
   // static TextView txv1, txv2, txv3;
    private GridView gridView;
    private Adap_Vid adaptador;
    String titulo;
    String fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_vid);
        //setTitle("Videos");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);

        toolbar.setLogo(R.drawable.imagen1234);

        Bundle datos = this.getIntent().getExtras();
        titulo=datos.getString("titulo");
        fecha=datos.getString("fecha");

       /* ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //rootView= getLayoutInflater().inflate(R.layout.act_vid, null);

        final ViddetController obj = new ViddetController("url","get", ActVid.this);
        obj.execute(GlobalVariables.vid_det.getCod_reg(),titulo,fecha);

       /* if(GlobalVariables.vid_det==null){
            obj.execute(GlobalVariables.cod_public);
        }else {

            obj.execute(GlobalVariables.vid_det.getCod_reg());
        }*/
        
        gridView = (GridView) findViewById(R.id.grid_vid);


      /*  imagv1 =(ImageView) findViewById(R.id.icon_viddet);
        txv1 = (TextView) findViewById(R.id.txv_publicador);
        txv2 = (TextView) findViewById(R.id.txvfecha);
        txv3 = (TextView) findViewById(R.id.vid_titulo);

        imagv1.setImageResource(R.drawable.ic_menu_video);


        txv1.setText(GlobalVariables.vid_det.getNom_publicador());
        txv2.setText(GlobalVariables.vid_det.getFecha());
        txv3.setText(GlobalVariables.vid_det.getTitulo());




        adaptador = new Adap_Vid(this);
        gridView.setAdapter(adaptador);*/
     //   gridView.setOnItemClickListener(this);

       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
*/


        setupToolBar();

        gridView.setOnItemClickListener(this);

      //      }
       // });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Vid_Gal item= (Vid_Gal) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, ActVidDet.class);
        intent.putExtra("post",position);
        //intent.putExtra("val",0);
        //intent.putExtra(ActVidDet.EXTRA_PARAM_ID, item.getId());
        startActivity(intent);
    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_flecha_retroceder);


        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }






}

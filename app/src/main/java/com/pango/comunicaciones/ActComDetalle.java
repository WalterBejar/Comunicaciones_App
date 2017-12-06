package com.pango.comunicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pango.comunicaciones.controller.ComdetController;

public class ActComDetalle extends AppCompatActivity {
    Button btn_adjuntos;
    //FrameLayout frame1;
    //WebView content;
    String titulo;
    String fecha;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_com_detalle);

        if(GlobalVariables.is_notification){
        //String idOffer = "";
        String title="";
        String date="";
        String code="";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcom);
        toolbar.setVisibility(View.GONE);
        linearLayout=(LinearLayout) findViewById(R.id.linear_lay);
        linearLayout.setVisibility(View.VISIBLE);
        ImageView flecha=(ImageView) findViewById(R.id.btn_retroceder);
        flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActComDetalle.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


            Intent startingIntent = getIntent();
        if (startingIntent != null) {
            code = startingIntent.getStringExtra("codigo"); // Retrieve the id
            title = startingIntent.getStringExtra("titulo"); // Retrieve the id
            date = startingIntent.getStringExtra("fecha"); // Retrieve the id
            //idOffer = startingIntent.getStringExtra("id_offer"); // Retrieve the id

        }

            final ComdetController obj = new ComdetController("url", "get", ActComDetalle.this);
            obj.execute(code, title, date);

        }else {

            setupToolBar();
            Bundle datos = this.getIntent().getExtras();
            titulo = datos.getString("titulo");
            fecha = datos.getString("fecha");

            final ComdetController obj = new ComdetController("url", "get", ActComDetalle.this);
            //obj.execute(GlobalVariables.com_pos.getCod_reg());
            if (GlobalVariables.com_pos == null) {
                //if (GlobalVariables.cod_public_com!=null){
                obj.execute(GlobalVariables.cod_public, titulo, fecha);
            }
            //else{obj.execute(GlobalVariables.cod_public_com);}
            //}

            else {
                obj.execute(GlobalVariables.com_pos.getCod_reg(), titulo, fecha);

            }


        }



        //adj_com=(ImageButton) findViewById(R.id.com_adj);
        btn_adjuntos=(Button) findViewById(R.id.btn_adjuntos);

        btn_adjuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se conecta a un activity//
                Intent intent = new Intent(ActComDetalle.this, ActComDes.class);
                startActivity(intent);
            }
        });

       /* frame1=(FrameLayout) findViewById(R.id.frame1);
        frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActComDetalle.this,"Error en el servidor",Toast.LENGTH_SHORT).show();
            }
        });*/
       /* content=(WebView) findViewById(R.id.com_visor);

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActComDetalle.this,"Error en el servidor",Toast.LENGTH_SHORT).show();

            }
        });*/


    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcom);
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

package com.pango.comunicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.pango.comunicaciones.controller.NotdetController;

public class ActNotDetalle extends AppCompatActivity {
    ImageButton adj;
    String titulo;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_not_detalle);
        setupToolBar();

        //setTitle("Noticias");

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnot);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.imagen1234);*/


        Bundle datos = this.getIntent().getExtras();
        titulo=datos.getString("titulo");
        fecha=datos.getString("fecha");

        NotdetController obj = new NotdetController("url","get", ActNotDetalle.this);
        obj.execute(GlobalVariables.not2pos.getCod_reg(),titulo,fecha);

/*
        if(GlobalVariables.not2pos==null){

            obj.execute(GlobalVariables.cod_public);

        }else {
            obj.execute(GlobalVariables.not2pos.getCod_reg());

        }*/

/*
        adj=(ImageButton) findViewById(R.id.ib_adj);

        adj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "click en el elemento de mi ListView");

                //se conecta a un activity//
                Intent intent = new Intent(ActNotDetalle.this, ActNotDes.class);
                startActivity(intent);

            }
        });*/


    }



    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnot);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.imagen1234);


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

package com.pango.comunicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.pango.comunicaciones.controller.ComdetController;

public class ActComDetalle extends AppCompatActivity {
    ImageButton adj_com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_com_detalle);
        setTitle("Eventos");

        final ComdetController obj = new ComdetController("url","get", ActComDetalle.this);
        //obj.execute(GlobalVariables.com_pos.getCod_reg());



        if(GlobalVariables.com_pos==null){
            //if (GlobalVariables.cod_public_com!=null){
            obj.execute(GlobalVariables.cod_public);}
        //else{obj.execute(GlobalVariables.cod_public_com);}
        //}
        else {
            obj.execute(GlobalVariables.com_pos.getCod_reg());

        }

        setupToolBar();

        adj_com=(ImageButton) findViewById(R.id.com_adj);
        adj_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "click en el elemento de mi ListView");

                //se conecta a un activity//
                Intent intent = new Intent(ActComDetalle.this, ActComDes.class);
                startActivity(intent);

            }
        });

    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcom);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }


}

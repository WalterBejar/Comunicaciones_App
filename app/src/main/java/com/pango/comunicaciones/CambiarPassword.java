package com.pango.comunicaciones;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pango.comunicaciones.controller.PassController;

public class CambiarPassword extends AppCompatActivity {
EditText pass,new_pass,rep_new_pass;
Button change_pass;
TextView tx_mensaje;
ImageButton i1,i2,i3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiar_password);

        //user=(EditText) findViewById(R.id.tx_user);
        pass=(EditText) findViewById(R.id.tx_pass_act);
        new_pass=(EditText) findViewById(R.id.tx_new_pass);
        rep_new_pass=(EditText) findViewById(R.id.tx_rep_new_pass);
        tx_mensaje=(TextView) findViewById(R.id.tx_mensaje);
        tx_mensaje.setText("");


        i1=(ImageButton) findViewById(R.id.btn_eliminar1);
        i2=(ImageButton) findViewById(R.id.btn_eliminar2);
        i3=(ImageButton) findViewById(R.id.btn_eliminar3);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setText("");
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass.setText("");
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rep_new_pass.setText("");
            }
        });


        change_pass=(Button) findViewById(R.id.button2);

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a=Utils.usuario;

                String b=pass.getText().toString();
                String c=new_pass.getText().toString();
                String d=rep_new_pass.getText().toString();

                if(b.isEmpty()||c.isEmpty()||d.isEmpty()){
                    //Toast.makeText(CambiarPassword.this,"Los campos no pueden estar vacíos",Toast.LENGTH_SHORT).show();
                    tx_mensaje.setText("Los campos no pueden estar vacíos");

                }else /*if(b.equals(c)){
                    Toast.makeText(CambiarPassword.this,"La nueva contraseña no puede ser igual a la actual",Toast.LENGTH_SHORT).show();

                }else*/ if(c.length()<5||c.length()>20){

                    //Toast.makeText(v.getContext(),"La contraseña debe tener entre 5 a 20 caracteres",Toast.LENGTH_SHORT).show();
                    tx_mensaje.setText("La contraseña debe tener entre 5 a 20 caracteres");
                }else  if(c.equals(d)){
                    tx_mensaje.setText("");

                    final PassController obj = new PassController(CambiarPassword.this, "url", "get");
                    obj.execute(a, b, c);


                }else {
                    //Toast.makeText(CambiarPassword.this,"Los campos de nueva contraseña y repetir nueva contraseña no coinciden",Toast.LENGTH_SHORT).show();
                    tx_mensaje.setText("Los campos de nueva contraseña y repetir nueva contraseña no coinciden");
                }



            }
        });


        setupToolBar();
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pass);
        //toolbar.setLogo(R.drawable.imagen1234);
        toolbar.setTitle("Reserva de Buses");
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

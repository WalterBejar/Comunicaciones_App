package com.pango.comunicaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pango.comunicaciones.controller.UpdateController;

public class Datos_Usuario extends AppCompatActivity {
TextView nombre,usuario,dni,email;
Button changepass, updateApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_usuario);
        setupToolBar();

        nombre=(TextView) findViewById(R.id.user_nombre);
        usuario=(TextView) findViewById(R.id.user_nickname);
        dni=(TextView) findViewById(R.id.user_dni);
        email=(TextView) findViewById(R.id.user_email);
        changepass=(Button) findViewById(R.id.btn_changepass);
        updateApp=(Button) findViewById(R.id.btn_UpdateApp);
        nombre.setText(Utils.nombres);
        usuario.setText(Utils.usuario);
        dni.setText(Utils.codPersona);
        email.setText(Utils.email);

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Datos_Usuario.this, CambiarPassword.class);
                startActivity(intent);
            }
        });
        if (Utils.esAdminWeb)
            updateApp.setVisibility(View.VISIBLE);
        else
            updateApp.setVisibility(View.GONE);

        updateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateController Update = new UpdateController(Datos_Usuario.this);
                Update.execute();
            }
        });
    }



    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
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

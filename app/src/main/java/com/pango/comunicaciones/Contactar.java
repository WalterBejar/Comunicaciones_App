package com.pango.comunicaciones;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pango.comunicaciones.controller.ContactarController;
import com.pango.comunicaciones.controller.ResPassController;

public class Contactar extends AppCompatActivity {
    private TextInputEditText tiet_asunto;
    private TextInputLayout til_asunto;
    Button btn_enviar;
    EditText et_mensaje;
    boolean flag_asunto=false;
    boolean flag_mensaje=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar);


        tiet_asunto = (TextInputEditText) findViewById(R.id.tiet_asunto);
        til_asunto = (TextInputLayout) findViewById(R.id.til_asunto);
        btn_enviar=(Button)  findViewById(R.id.btn_enviar);
        et_mensaje=(EditText) findViewById(R.id.et_mensaje);

        btn_enviar.setEnabled(false);


        tiet_asunto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //btn_enviar.setEnabled(false);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // btn_enviar.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {
                String a=s.toString().trim();
                if(a.equals("")) {
                    btn_enviar.setEnabled(false);
                    flag_asunto=false;
                }else if(flag_mensaje){
                    btn_enviar.setEnabled(true);
                    flag_asunto=true;
                }else{
                    flag_asunto=true;
                }
            }
        });

        et_mensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String a=s.toString().trim();
                if(a.equals("")) {
                    btn_enviar.setEnabled(false);
                    flag_mensaje=false;
                }else if(flag_asunto){
                    btn_enviar.setEnabled(true);
                    flag_mensaje=true;
                }else {flag_mensaje=true;}
            }
        });
    }
    public void BtnEnviar(View view){
        String asunto=tiet_asunto.getText().toString();
        String mensaje=et_mensaje.getText().toString();
        ContactarController contactarAdmin = new ContactarController(asunto,mensaje, "post", Contactar.this);
        contactarAdmin.execute();
    }
}

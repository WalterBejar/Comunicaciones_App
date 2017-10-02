package com.pango.comunicaciones;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.pango.comunicaciones.controller.DataController;

public class SplashScreenActivity extends AppCompatActivity {
    //private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen_activity);
        //GlobalVariables.Urlbase=Recuperar_data();

       // GlobalVariables.Urlbase="https://app.antapaccay.com.pe/Proportal/SCOM_Service/api/";
/*
if(GlobalVariables.Urlbase.equals(null)) {
    Intent mainIntent = new Intent()
            .setClass(SplashScreenActivity.this, MainActivity.class);
    startActivity(mainIntent);
    finish();
}else {

    getToken gettoken = new getToken();
    gettoken.getToken();
}
*/
      /*  getToken gettoken = new getToken();
        gettoken.getToken();

        int y = GlobalVariables.con_status;
        if (y==200){

*/



        final DataController obj = new DataController("url","get", SplashScreenActivity.this);
        obj.execute(String.valueOf(1),String.valueOf(10));

       // String a=obj.getStatus().toString();
       // String b= AsyncTask.Status.FINISHED.toString();




        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (obj.getStatus() == AsyncTask.Status.FINISHED) {
                    Intent mainIntent = new Intent()
                            .setClass(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                    h.postDelayed(this, 250);
                }


            }
        }, 250);



  /*      }else{

            //Toast.makeText(this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent()
                    .setClass(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }*/


        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                // finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);*/
    }

    public String Recuperar_data() {

        SharedPreferences settings =  getSharedPreferences("datos", Context.MODE_PRIVATE);
        String url_servidor = settings.getString("url","");
        //Toast.makeText(this, url_servidor, Toast.LENGTH_SHORT).show();
        return url_servidor;
    }


}
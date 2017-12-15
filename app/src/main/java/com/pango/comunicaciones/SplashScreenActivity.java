package com.pango.comunicaciones;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DELAY = 800;
    TextView text_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen_activity);

        ConnectivityManager cmanager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info= cmanager.getActiveNetworkInfo();
      // Boolean red= GlobalVariables.isOnlineNet();
        text_logo=(TextView) findViewById(R.id.text_logo);



        Typeface face1=Typeface.createFromAsset(getAssets(),"fonts/HelveticaIt.ttf");
        text_logo.setTypeface(face1);
        text_logo.setText("Antapaccay móvil");



        if(info!=null&&info.isConnected()){
            if (info.getType() == ConnectivityManager.TYPE_WIFI||info.getType()==ConnectivityManager.TYPE_MOBILE) {

                //Toast.makeText(SplashScreenActivity.this, "Wifi",Toast.LENGTH_LONG).show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        /*if(GlobalVariables.flag_notificacion){
                            //Intent
                            Intent intent = new Intent(SplashScreenActivity.this, ActComDetalle.class);

                            intent.putExtra("titulo","");
                            intent.putExtra("fecha","");

                            startActivity(intent);

                            finish();
                        }else {*/
                            // Start the next activity
                            Intent mainIntent = new Intent().setClass(
                                    SplashScreenActivity.this, MainActivity.class);
                           // mainIntent.putExtra("respuesta", false); //Optional parameters
                            startActivity(mainIntent);
                            finish();
                        //}
                        // Close the activity so the user won't able to go back this
                        // activity pressing Back button
                        // finish();




                    }
                };

                // Simulate a long loading process on application startup.
                Timer timer = new Timer();
                timer.schedule(task, SPLASH_SCREEN_DELAY);

            }
        }else {

            //Toast.makeText(SplashScreenActivity.this, "Not connected",Toast.LENGTH_LONG).show();


            AlertDialog alertDialog = new AlertDialog.Builder(SplashScreenActivity.this).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Error en la Conexión");
            alertDialog.setMessage("Revisa tu conexión a internet e inténtalo de nuevo");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Inténtalo de nuevo", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(getIntent());
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cerrar Aplicación", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    //startActivity(getIntent());
                }
            });

            alertDialog.show();

        }








        /*
        ConnectivityManager cmanager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info= cmanager.getActiveNetworkInfo();

        final DataController obj = new DataController("url", "get", SplashScreenActivity.this);
        obj.execute(String.valueOf(1), String.valueOf(10));

        // String a=obj.getStatus().toString();
        // String b= AsyncTask.Status.FINISHED.toString();
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (obj.getStatus() == AsyncTask.Status.FINISHED) {

                    if(GlobalVariables.con_status==200){
                        Intent mainIntent = new Intent()
                                .setClass(SplashScreenActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else{

                        //Toast.makeText(getApplicationContext(), "error en la conexion", Toast.LENGTH_SHORT).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(SplashScreenActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Error en la Conexión");
                        alertDialog.setMessage("Revisa tu conexión a internet e inténtalo de nuevo");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Inténtalo de nuevo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                startActivity(getIntent());
                            }
                        });

                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cerrar Aplicación", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                //startActivity(getIntent());
                            }
                        });

                        alertDialog.show();

                    }

                } else {
                    h.postDelayed(this, 250);
                }


            }
        }, 250);

*/












    }




    }

  /*  public String Recuperar_data() {

        SharedPreferences settings =  getSharedPreferences("datos", Context.MODE_PRIVATE);
        String url_servidor = settings.getString("url","");
        //Toast.makeText(this, url_servidor, Toast.LENGTH_SHORT).show();
        return url_servidor;
    }
*/



    /*      }else{

            //Toast.makeText(this, "Error en el servidor", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent()
                    .setClass(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }*/





//finish();
                      /* AlertDialog.Builder dialogo = new AlertDialog.Builder(getApplicationContext());
                        dialogo.setTitle("Error de conexión");
                        dialogo.setMessage("Es posible que la url sea incorrecta, ¿Que deseas hacer?");
                        dialogo.setPositiveButton("Configuración", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        dialogo.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialogo.create();
                        dialogo.show();*/

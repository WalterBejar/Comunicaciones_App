package com.pango.comunicaciones;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class ActVidDet extends AppCompatActivity {
   // public static final String EXTRA_PARAM_ID = "";
    //public static final String VIEW_NAME_HEADER_IMAGE = "";
    //ActVid reg=new ActVid();

    private int position;
    public int valor;
    //private Vid_Gal itemDetallado;

    private VideoView videoExtendida;
    //private PhotoViewAttacher photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ProgressDialog pDialog;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
/*
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        setContentView(R.layout.act_vid_det);

        Bundle datos = this.getIntent().getExtras();
        position=datos.getInt("post");
        //valor=datos.getInt("valor");
        //Registrar(valor);

        if(GlobalVariables.cont_posvid==0){
            Registrar(0);
            GlobalVariables.cont_posvid=GlobalVariables.cont_posvid+1;
        }
       // itemDetallado = Vid_Gal.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
        videoExtendida = (VideoView) findViewById(R.id.video_extendida);

        /*Uri vidUri = Uri.parse(itemDetallado.getUrl());
        videoExtendida.setVideoURI(vidUri);
*/
        // Create a progressbar
        pDialog = new ProgressDialog(ActVidDet.this);
        // Set progressbar title
        // pDialog.setTitle("Cargando Videos");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        // Show progressbar
        pDialog.show();


        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    ActVidDet.this);
            mediacontroller.setAnchorView(videoExtendida);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(GlobalVariables.Urlbase+GlobalVariables.listdetvid.get(position).getUrl_vid());

            videoExtendida.setMediaController(mediacontroller);
            videoExtendida.setVideoURI(video);

//////////////////////////////////////////////////////////////////////////////////////
            /*if(savedInstanceState!=null){
                int currentPos =  savedInstanceState.getInt("current position");
                videoExtendida.seekTo(currentPos);
            }*/
 ////////////////////////////////////////////////////////////////////////////////////////////7

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoExtendida.requestFocus();
        videoExtendida.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                if (pDialog.isShowing()) pDialog.dismiss();


                videoExtendida.start();
                if (position == 0) {
                    // Si tenemos una posición guardada, el vídeo comienza ahí.
                    //videoExtendida.start();
                    videoExtendida.start();


                } else {
                    // Si nuestro activity se reanuda, ponemos pausa.
                    //videoExtendida.pause();
                    videoExtendida.pause();
                }




            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        /* Usamos onSaveInstanceState para guardar la posición de
           reproducción del vídeo en caso de un cambio de orientación. */
       // reg.Registrar(position);
        Registrar(position);

        savedInstanceState.putInt("Position",position);

        // videoExtendida.getCurrentPosition()
       // videoExtendida.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
         * Usamos onRestoreInstanceState para reproducir el vídeo
         * desde la posición guardada.
         */


        position = savedInstanceState.getInt("Position");
        videoExtendida.seekTo(position);
        videoExtendida.pause();


    }


    @Override
    protected void onPause() {
        super.onPause();
        // Se obtiene la posición actual de la reproducción antes de la pausa.

        if( videoExtendida.getCurrentPosition()>0) {

        position = videoExtendida.getCurrentPosition();
           Registrar(position);
        }


        }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume called");

        int posi= Integer.parseInt(Recuperar_data());
        videoExtendida.seekTo(posi);
        videoExtendida.start(); //Or use resume() if it doesn't work. I'm not sure
    }

    public void Registrar(int mPosition) {
        SharedPreferences posicion_vid = this.getSharedPreferences("pos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = posicion_vid.edit();
        editor.putString("pos_url", String.valueOf(mPosition));

        editor.commit();
        //v.finish();
    }



    public String Recuperar_data() {

        SharedPreferences settings =  this.getSharedPreferences("pos", Context.MODE_PRIVATE);
        String dominio_user = settings.getString("pos_url","valorpordefecto");

        //Toast.makeText(this.getActivity(), nombre, Toast.LENGTH_SHORT).show();

       // Toast.makeText(this,"se recupero"+dominio_user, Toast.LENGTH_SHORT).show();
        return dominio_user;
    }


}

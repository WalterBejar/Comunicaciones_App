package com.pango.comunicaciones;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.pango.comunicaciones.controller.ValidUrlController;

import java.io.IOException;

public class ActVidDet extends AppCompatActivity implements SurfaceHolder.Callback,MediaPlayer.OnPreparedListener,MediaController.MediaPlayerControl{
   // public static final String EXTRA_PARAM_ID = "";
    //public static final String VIEW_NAME_HEADER_IMAGE = "";
    //ActVid reg=new ActVid();
  //  private static final String video= "https://app.antapaccay.com.pe/Proportal/SCOM_Service/Videos/2616_SD.mp4";
    String video;
    public int length=0;

    private VideoView surfaceView;
    private ConstraintLayout top, button;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private MediaController mediaController;
    private Handler handler = new Handler();
    public PowerManager.WakeLock wakelock;
    //private static final String video= "https://app.antapaccay.com.pe/Proportal/SCOM_Service/Videos/2616_SD.mp4";
    public ProgressDialog pDialog;
    private SeekBar seekbar;
    public int percenloadin;

    public boolean isListvideo;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*final ProgressDialog pDialog;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        super.onCreate(savedInstanceState);
        percenloadin=0;

        setContentView(R.layout.act_vid_det);
        Bundle datos = this.getIntent().getExtras();
        position=datos.getInt("post");
        isListvideo=datos.getBoolean("isList");
        obtener_estado();
        Registrar(0);


        if(isListvideo==true) {

            String video_data=datos.getString("urltemp");

            video=GlobalVariables.Urlbase.substring(0, GlobalVariables.Urlbase.length() - 4)+video_data.replace(".",GlobalVariables.cal_sd_hd);//+video_data.replace(".",GlobalVariables.cal_sd_hd);

        }else{
            video = GlobalVariables.Urlbase.substring(0, GlobalVariables.Urlbase.length() - 4) + GlobalVariables.listdetvid.get(position).getUrl_vid().replace(".",GlobalVariables.cal_sd_hd);
        }

       /* final ValidUrlController obj1 = new ValidUrlController(ActVidDet.this,"url","get");
        obj1.execute(video);
*/

        /*final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (obj1.getStatus() == AsyncTask.Status.FINISHED) {



                } else {
                    h.postDelayed(this, 50);
                }
            }
        }, 250);*/

if(GlobalVariables.con_status_video==200) {
    surfaceView = (VideoView) findViewById(R.id.surfaceView);
    surfaceHolder = surfaceView.getHolder();
    surfaceHolder.addCallback(this);
    surfaceView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mediaController != null) {
                mediaController.show();
            }
            return false;
        }
    });


    top = (ConstraintLayout) findViewById(R.id.padingtop);
    button = (ConstraintLayout) findViewById(R.id.padingbutton);

    pDialog = new ProgressDialog(ActVidDet.this);
    pDialog.setMessage("Buffering...");
    pDialog.setIndeterminate(false);
    pDialog.setCancelable(true);
    pDialog.show();

    final PowerManager pm = (PowerManager) getSystemService(getBaseContext().POWER_SERVICE);
    this.wakelock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "etiqueta");
    surfaceView.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mediaController != null) {
                mediaController.show();
            }
            return false;
        }
    });


}else{
    AlertDialog alertDialog = new AlertDialog.Builder(ActVidDet.this).create();
    alertDialog.setCancelable(false);
    alertDialog.setTitle("Error en la Reproducción");
    alertDialog.setMessage("El video no se encuentra disponible");
    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            finish();
            //startActivity(getIntent());
        }
    });

                /*alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cerrar Aplicación", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //startActivity(getIntent());
                    }
                });*/

    alertDialog.show();
}


    }

    //////////////////////////////////////////////77





    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) //To fullscreen
        {
            top.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));

        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            top.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.38f));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(mediaPlayer !=null) mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer !=null){
            mediaPlayer.pause();
            Registrar(mediaPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (pDialog.isShowing()) pDialog.dismiss();
        mediaPlayer.seekTo( Integer.parseInt(Recuperar_data()));
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(surfaceView);
        handler.post(new Runnable() {

            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });

        this.wakelock.acquire();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Registrar(0);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wakelock.release();
                    }
                },3000);
            }
        });

        int topContainerId = getResources().getIdentifier("mediacontroller_progress", "id", "android");
        seekbar = (SeekBar) mediaController.findViewById(topContainerId);

        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                percenloadin=percent;
            }
        });

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(surfaceHolder);
        try{
            mediaPlayer.setDataSource(video);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            mediaController = new MediaController(this);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

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
        String dominio_user = settings.getString("pos_url","0");
        return dominio_user;
    }

    public void releaseMediaPlayer(){
        if(mediaPlayer!= null)
        {
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    @Override
    public void start() {
        mediaPlayer.start();
        this.wakelock.acquire();
    }

    @Override
    public void pause() {
        wakelock.release();
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return percenloadin;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    public void obtener_estado(){
        SharedPreferences cal_vid =  this.getSharedPreferences("calidad", Context.MODE_PRIVATE);
        Boolean calvid = cal_vid.getBoolean("cal",false);

        if(calvid){
            GlobalVariables.cal_sd_hd=".";
        }else{
            GlobalVariables.cal_sd_hd="_SD.";

        }

    }

}

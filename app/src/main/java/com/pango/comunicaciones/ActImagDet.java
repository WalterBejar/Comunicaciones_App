package com.pango.comunicaciones;

import android.*;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.adapter.ViewPagerAdapter;

import uk.co.senab.photoview.PhotoViewAttacher;

//import com.allenxuan.xuanyihuang.xuanimageview.XuanImageView;


public class ActImagDet extends AppCompatActivity {

    public static final String EXTRA_PARAM_ID = "";
    public static final String VIEW_NAME_HEADER_IMAGE = "";
    TouchImageView imagenExtendida;
    PhotoViewAttacher photoViewAttacher;
    int positionIn;

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    int pos_img;
    boolean isinitial=true;
    LayoutInflater layoutInflater;
    View popupView;
    PopupWindow popupWindow;
    Button btn_des;
    private static final short REQUEST_CODE = 6545;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_imag_det);
        //setTitle("Fotos");

        Bundle datos = this.getIntent().getExtras();
        positionIn=datos.getInt("post");
        isinitial=datos.getBoolean("isInitial");

        imagenExtendida = (TouchImageView) findViewById(R.id.imagen_extendida);

        btn_des=(Button) findViewById(R.id.btn_des);

       // cargarImagenExtendida();

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(ActImagDet.this,GlobalVariables.listdetimg,positionIn);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(positionIn,true);









        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                //System.out.println("Current position=="+num);
                //textView.setText((arg0+1)+" of "+ GlobalVariables.listdetimg.size());
                //Toast.makeText(getApplicationContext(), "Descargando..."+arg0, Toast.LENGTH_SHORT).show();
                pos_img=arg0;
                isinitial=false;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                //System.out.println("onPageScrolled");
            }

            @Override
            public void onPageScrollStateChanged(int num) {
                // TODO Auto-generated method stub

            }
        });




    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("image_array", GlobalVariables.listdetimg);
        outState.putInt("savedImagePosition",positionIn);

    }


    public void download(View view) {

        //executeDownload();

        if (isDownloadManagerAvailable()) {
            checkSelfPermission();
            //executeDownload();

        } else {
            Toast.makeText(this, "Download manager is not available", Toast.LENGTH_LONG).show();
        }


    }





    private void executeDownload() {

        //DownloadManager downloadManager;
        String url_image="";
        String nombre="";

        if(isinitial){
            url_image= GlobalVariables.Urlbase.substring(0,GlobalVariables.Urlbase.length()-4)+ GlobalVariables.listdetimg.get(positionIn).getUrl_img();
            nombre=GlobalVariables.listdetimg.get(positionIn).getUrl_img();
        }else{
            url_image= GlobalVariables.Urlbase.substring(0,GlobalVariables.Urlbase.length()-4)+ GlobalVariables.listdetimg.get(pos_img).getUrl_img();
            nombre=GlobalVariables.listdetimg.get(pos_img).getUrl_img();
        }
        String[] nom_Img=nombre.split("/");



        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url_image));
        //request.setDescription("Downloading file " + NAME_FILE);
        //request.setTitle("Downloading");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nom_Img[1]);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);


        Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();



    }




    private static boolean isDownloadManagerAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }



    private void checkSelfPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } else {

            executeDownload();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted! Do the work
                    executeDownload();
                } else {
                    // permission denied!
                    Toast.makeText(this, "Para poder descargar la imagen, otorga los permisos  ", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



}

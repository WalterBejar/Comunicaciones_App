package com.pango.comunicaciones;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pango.comunicaciones.adapter.ViewPagerAdapter;
import com.pango.comunicaciones.adapter.ViewPagerAdapter2;
import com.pango.comunicaciones.controller.ListImgNotController;
import com.pango.comunicaciones.controller.ListImgdetController;

import java.util.ArrayList;

import static com.pango.comunicaciones.GlobalVariables.flag_orienta;


public class ActImgNot extends AppCompatActivity {
    public static String URL_IMG_NOT = "";
    String CodReg;
    TouchImageView img_not;
    ProgressDialog progressDialog;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<String> img_final=new ArrayList<String>();
    int positionIn;

    Button btn_des;
    boolean isinitial=true;
    int pos_img;
    private static final short REQUEST_CODE = 6545;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_img_not);

       // progressDialog = ProgressDialog.show(this, "Loading", "Cargando...");

        Bundle datos = this.getIntent().getExtras();
        CodReg=datos.getString("codreg");
        positionIn=datos.getInt("posIn");
        //URL_IMG_NOT=datos.getString("url_img");

        img_not = (TouchImageView) findViewById(R.id.imagen_extendida);
        viewPager = (ViewPager) findViewById(R.id.viewPager3);
        btn_des=(Button) findViewById(R.id.btn_des);


        String orientacion=Utils.getRotation(this);

        if(orientacion.equals("vertical")&&flag_orienta==true) {
            flag_orienta=false;
            final ListImgNotController obj = new ListImgNotController("url","get", ActImgNot.this);
            obj.execute(CodReg,String.valueOf(positionIn));


            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (obj.getStatus() == AsyncTask.Status.FINISHED) {

                        adapter = new ViewPagerAdapter(ActImgNot.this,GlobalVariables.listdetimg,positionIn);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(positionIn,true);

                    } else {
                        h.postDelayed(this, 250);
                    }

                }
            }, 250);




        }else{
            adapter = new ViewPagerAdapter(this,GlobalVariables.listdetimg,positionIn);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(positionIn,true);
        }
        /*
            btn_des.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DownloadManager downloadManager;
                    String url_image="";

                    if(isinitial){
                        url_image= GlobalVariables.Urlbase.substring(0,GlobalVariables.Urlbase.length()-4)+ GlobalVariables.listdetimg.get(positionIn).getUrl_img();

                    }else{
                        url_image= GlobalVariables.Urlbase.substring(0,GlobalVariables.Urlbase.length()-4)+ GlobalVariables.listdetimg.get(pos_img).getUrl_img();

                    }
                    downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    //Uri uri=Uri.parse("http://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf9GsdJZOxuApw8q86bV211L8tPhh1RB3zj6qIJbfVV9HwIBwlfg");
                    //String url_serv= ad;
                    //String url_serv="http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal   bug.png";
                    //String cadMod= Utils.ChangeUrl(url_serv);
                    //;
                    Uri uri=Uri.parse(url_image);
                    // Uri uri=Uri.parse("http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal%20bug.png");
                    DownloadManager.Request request= new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();


                }
            });

*/

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    // TODO Auto-generated method stub
                    //System.out.println("Current position=="+num);
                    //textView.setText((arg0+1)+" of "+ GlobalVariables.listdetimg.size());
                    //Toast.makeText(getApplicationContext(), "Descargando...actsw"+arg0, Toast.LENGTH_SHORT).show();
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










      /*  viewPager = (ViewPager)findViewById(R.id.viewPager2);
        adapter = new ViewPagerAdapter2(ActImgNot.this,img_final,0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0,true);

*/



       // cargarImagenExtendida();
        //progressDialog.dismiss();

    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("image_array", GlobalVariables.listdetimg);
        outState.putInt("savedImagePosition",0);
        //outState.putInt("savedPositionP",posPublic);


    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("image_array", GlobalVariables.listdetimg);
        outState.putInt("savedImagePosition",positionIn);
        //outState.putInt("savedPositionP",posPublic);


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
                    Toast.makeText(this, "Para poder descargar la imagen, otorga los permisos ", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }















}

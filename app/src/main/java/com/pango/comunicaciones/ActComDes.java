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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.pango.comunicaciones.adapter.DesComAdap;


public class ActComDes extends AppCompatActivity {

    ListView com_listdes;

    ImageButton combutton;
    DownloadManager downloadManager;
    private static final short REQUEST_CODE = 6545;
    String cadMod="";
    String nombre_doc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_com_des);

        com_listdes=(ListView) findViewById(R.id.com_list_des);
        //setTitle("Adjuntos");

        DesComAdap ca = new DesComAdap(getApplicationContext(), GlobalVariables.listdetcom);
        com_listdes.setAdapter(ca);



        com_listdes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String url_serv= GlobalVariables.Urlbase+ GlobalVariables.listdetcom.get(position).getUrl_file();
                nombre_doc=GlobalVariables.listdetcom.get(position).getNombre();
                //String url_serv="http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal   bug.png";
                cadMod=Utils.ChangeUrl(url_serv);



                if (isDownloadManagerAvailable()) {
                    checkSelfPermission();
                    //executeDownload();

                } else {
                    Toast.makeText(ActComDes.this, "Download manager is not available", Toast.LENGTH_LONG).show();
                }





/*

                //int pos=position;
                downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                //Uri uri=Uri.parse("http://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf9GsdJZOxuApw8q86bV211L8tPhh1RB3zj6qIJbfVV9HwIBwlfg");

                        //;

                Uri uri=Uri.parse(cadMod);
                // Uri uri=Uri.parse("http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal%20bug.png");


                DownloadManager.Request request= new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();

*/



//%20
                //   Toast.makeText(context,"Boton detalles: "+position,Toast.LENGTH_LONG);

            }
        });


        setupToolBar();

    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbar_comdes);
        toolbar.setLogo(R.drawable.imagen1234);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_flecha_retroceder);


        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }




    private static boolean isDownloadManagerAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }


    private void checkSelfPermission() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

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
                    Toast.makeText(this, "Please give permissions ", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void executeDownload() {

        // registrer receiver in order to verify when download is complete
        //registerReceiver(new DonwloadCompleteReceiver(), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(cadMod));
        //request.setDescription("Downloading file " + NAME_FILE);
        //request.setTitle("Downloading");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombre_doc);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);












    }








}

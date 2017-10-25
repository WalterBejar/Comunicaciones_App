package com.pango.comunicaciones;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_com_des);

        com_listdes=(ListView) findViewById(R.id.com_list_des);
        setTitle("Adjuntos");

        DesComAdap ca = new DesComAdap(getApplicationContext(), GlobalVariables.listdetcom);
        com_listdes.setAdapter(ca);



        com_listdes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos=position;
                downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                //Uri uri=Uri.parse("http://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf9GsdJZOxuApw8q86bV211L8tPhh1RB3zj6qIJbfVV9HwIBwlfg");
                String url_serv= GlobalVariables.Urlbase+ GlobalVariables.listdetcom.get(position).getUrl_file();
                //String url_serv="http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal   bug.png";
                String cadMod=Utils.ChangeUrl(url_serv);



                        //;

                Uri uri=Uri.parse(cadMod);
                // Uri uri=Uri.parse("http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal%20bug.png");


                DownloadManager.Request request= new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
                Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();


//%20
                //   Toast.makeText(context,"Boton detalles: "+position,Toast.LENGTH_LONG);

            }
        });


        setupToolBar();

    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbar_comdes);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }
}

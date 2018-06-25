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

import com.pango.comunicaciones.adapter.DescarAdap;


public class ActNotDes extends AppCompatActivity {
    ListView listdescarga;

    ImageButton button;
    DownloadManager downloadManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_not_des);
        setTitle("Adjuntos");


        listdescarga=(ListView) findViewById(R.id.list_des);

        DescarAdap ca = new DescarAdap(getApplicationContext(),GlobalVariables.listdetnot);
        listdescarga.setAdapter(ca);

/*
        button=(ImageButton) findViewById(R.id.icon_des);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri=Uri.parse("http://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf9GsdJZOxuApw8q86bV211L8tPhh1RB3zj6qIJbfVV9HwIBwlfg");
                DownloadManager.Request request= new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);

            }
        });

*/
        listdescarga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        //Uri uri=Uri.parse("http://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQf9GsdJZOxuApw8q86bV211L8tPhh1RB3zj6qIJbfVV9HwIBwlfg");
                       String url_serv=GlobalVariables.Urlbase+GlobalVariables.listdetnot.get(position).getUrl_file();
                        //String url_serv="http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal   bug.png";
                       Uri uri=Uri.parse(url_serv.replaceAll("\\s","%20"));
                       // Uri uri=Uri.parse("http://192.168.1.214/SCOM_Service/api/multimedia/GetImagen/182/portal%20bug.png");

                        DownloadManager.Request request= new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        Long reference = downloadManager.enqueue(request);

                Toast.makeText(getApplicationContext(), "Descargando...", Toast.LENGTH_SHORT).show();
            }
        });


        setupToolBar();

    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbar_notdes);

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

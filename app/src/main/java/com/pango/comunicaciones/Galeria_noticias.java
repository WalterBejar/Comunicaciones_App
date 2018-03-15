package com.pango.comunicaciones;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pango.comunicaciones.controller.GalNotController;
import com.pango.comunicaciones.model.Img_Gal;

public class Galeria_noticias extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_noticias);
        setupToolBar();

        final GalNotController obj = new GalNotController("url","get", Galeria_noticias.this);
        obj.execute(GlobalVariables.data_fotos.get(2),GlobalVariables.data_fotos.get(0),GlobalVariables.data_fotos.get(1) );

        gridView = (GridView) findViewById(R.id.grid_notgal);

        gridView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Img_Gal item= (Img_Gal) parent.getItemAtPosition(position);

        int a= item.getId();

        Intent intent = new Intent(this, ActImagDet.class);
        intent.putExtra(ActImagDet.EXTRA_PARAM_ID, a);
        intent.putExtra("post",position);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.img_adap),
                                    ActImagDet.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else
            startActivity(intent);


    }







    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarnotgal);

        if (toolbar == null) return;

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.imagen1234);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_flecha_retroceder);
        getSupportActionBar().setTitle("");


        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }

}

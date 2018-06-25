package com.pango.comunicaciones;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Field;
import java.util.Stack;

import layout.FragmentComunicados;
import layout.FragmentConfiguracion;
import layout.FragmentContactenos;
import layout.FragmentImagenes;
import layout.FragmentNoticias;
import layout.FragmentNotificacion;
import layout.FragmentRedesSociales;
import layout.FragmentTickets;
import layout.FragmentVideos;

import layout.FragmentInicio2;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        FragmentInicio2.OnFragmentInteractionListener,
        FragmentNoticias.OnFragmentInteractionListener,
        FragmentComunicados.OnFragmentInteractionListener,
        FragmentImagenes.OnFragmentInteractionListener,
        FragmentVideos.OnFragmentInteractionListener,
        FragmentTickets.OnFragmentInteractionListener,
        FragmentNotificacion.OnFragmentInteractionListener,
        FragmentConfiguracion.OnFragmentInteractionListener,
        FragmentContactenos.OnFragmentInteractionListener,
        FragmentRedesSociales.OnFragmentInteractionListener
{
    //  private int mSelectedItem;
    //  private static final String SELECTED_ITEM = "arg_selected_item";
    //private Stack<Fragment> fragmentStack;

    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    Toolbar toolbar;




    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public enum NavigationFragment{
        Inicio,
        Noticias,
        Comunicados,
        Imagenes,
        Videos,
        Tickets,
        Notificacion,
        Configuracion,
        Contactenos,
        RedesSociales

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_logotitulo);
        getSupportActionBar().setTitle("");
        toolbar.setLogo(R.drawable.imagen12345);



        FirebaseMessaging.getInstance().subscribeToTopic("/topics/notificaciones");
        GlobalVariables.id_phone= "Android@"+Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
        //fragmentManager = getSupportFragmentManager();
        //toolbar.setAlpha(1);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_logotitulo);
        //GlobalVariables.Urlbase=Recuperar_data();
        // Intent toReservaTicketFiltro = new Intent(getApplicationContext(), ReservaTicketFiltro.class);
        //startActivity(toReservaTicketFiltro);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GlobalVariables.anchoMovil=metrics.widthPixels/2;
        //int width = metrics.widthPixels; // ancho absoluto en pixels
        //int height = metrics.heightPixels; // alto absoluto en pixels


        //toolbar

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        //bottomNavigationView.setVisibility(View.GONE);

        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ChangeFragment(NavigationFragment.Inicio);
        uncheckItemsMenu();
        bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);
        bottomNavigationView.setVisibility(View.GONE);

        getSupportActionBar().setTitle("");

        // ponemos el contenido inicial
    /*    Bundle extras = getIntent().getExtras();
        if(extras.getBoolean("respuesta")){
            ChangeFragment(NavigationFragment.Tickets);
            bottomNavigationView.getMenu().findItem(R.id.navigation_tickets).setChecked(true);
            bottomNavigationView.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Tu sesión ha expirado ...",Toast.LENGTH_SHORT).show();


        }
        else {
            ChangeFragment(NavigationFragment.Inicio);
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);
            bottomNavigationView.setVisibility(View.GONE);

            getSupportActionBar().setTitle("");
        }*/
/*************************ver arriba***************************/
        if(GlobalVariables.flag_notificacion==true){
            //Intent
            Intent intent = new Intent(this, ActComDetalle.class);

            intent.putExtra("titulo","");
            intent.putExtra("fecha","");

            startActivity(intent);
        }

    }


    ///fin oncreate

    //int  backpress=0;
    private Boolean exit = false;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    try {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }else if (GlobalVariables.fragmentStack.size() == 2) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            GlobalVariables.fragmentStack.lastElement().onPause();
            ft.remove(GlobalVariables.fragmentStack.pop());
            GlobalVariables.fragmentStack.lastElement().onResume();

            //Fragment fra=GlobalVariables.fragmentStack.lastElement();

            ft.show(GlobalVariables.fragmentStack.lastElement());

            ft.commit();

            bottomNavigationView.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);


            // ChangeFragment(NavigationFragment.Inicio);
        }else {
        //super.onBackPressed();
            if (exit) {
                GlobalVariables.fragmentStack.clear();
                super.onBackPressed(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }

        }


    }catch (Throwable e){
        Log.d("error_frag", e.getLocalizedMessage());
    }





    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
    //menu lateral
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_noticias) {
            ClickMenuNoticias();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_noticias).setChecked(true);
        } else if (id == R.id.nav_publicaciones) {
            ClickMenuComunicados();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);
        } else if (id == R.id.nav_imagenes) {
            ClickMenuImagenes();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_imagenes).setChecked(true);
        } else if (id == R.id.nav_videos) {
            ClickMenuVideos();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_videos).setChecked(true);//menu inferior
        } else if (id == R.id.nav_tickets) {
            ClickMenuTickets();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_tickets).setChecked(true);
        }else if (id == R.id.nav_notificaciones){
            ClickMenuNotificacion();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);

        }else if (id == R.id.nav_configuracion){
            ClickMenuConfiguracion();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);
        }else if (id == R.id.nav_Contactenos){
            ClickMenuContactenos();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);

        }else if (id == R.id.nav_RedesSociales){
            ClickMenuRedesSociales();
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //menu inferior
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    //setTitle("Antapaccay");
                    ChangeFragment(NavigationFragment.Inicio);
                    bottomNavigationView.setVisibility(View.GONE);

                    uncheckItemsMenu();
                    return true;
                case R.id.navigation_imagenes:
                    uncheckItemsMenu();
                    //navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(true);
                    ClickMenuImagenes();
                    return true;
                case R.id.navigation_videos:
                    uncheckItemsMenu();

                    ClickMenuVideos();
                    //navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
                    return true;
                case R.id.navigation_noticias:
                    uncheckItemsMenu();

                    ClickMenuNoticias();
                    return true;
                case R.id.navigation_tickets:


                    uncheckItemsMenu();
                    //navigationView.getMenu().findItem(R.id.nav_publicaciones).setChecked(true);
                    ClickMenuTickets();
                    return true;
            }
            return false;
        }

    };

    public void ClickMenuNoticias() {
        uncheckItemsMenu();
        //setTitle("Noticias");
        bottomNavigationView.getMenu().findItem(R.id.navigation_noticias).setChecked(true);
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_noticias).setChecked(true);
        ChangeFragment(NavigationFragment.Noticias);
    }

    public void ClickMenuComunicados() {

        uncheckItemsMenu();
        //setTitle("Eventos");

        //bottomNavigationView.getMenu().findItem(R.id.navigation_publicaciones).setChecked(true);
       //  bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_publicaciones).setChecked(true);
        ChangeFragment(NavigationFragment.Comunicados);
    }
    public void ClickMenuImagenes() {
        uncheckItemsMenu();
        //setTitle("Fotos");

        bottomNavigationView.getMenu().findItem(R.id.navigation_imagenes).setChecked(true);
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(true);
        ChangeFragment(NavigationFragment.Imagenes);

    }
    public void ClickMenuVideos() {
        uncheckItemsMenu();
        //setTitle("Videos");
        bottomNavigationView.getMenu().findItem(R.id.navigation_videos).setChecked(true);
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
        ChangeFragment(NavigationFragment.Videos);
    }
    //opciones menu lateral
    private void ClickMenuTickets() {
        uncheckItemsMenu();
        //setTitle("Reserva de Buses");
        bottomNavigationView.getMenu().findItem(R.id.navigation_tickets).setChecked(true);
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_tickets).setChecked(true);
        //bottomNavigationView.setVisibility(View.VISIBLE);

        ChangeFragment(NavigationFragment.Tickets);
    }

    private void ClickMenuNotificacion() {
        uncheckItemsMenu();
        //setTitle("Notificaciones");
        bottomNavigationView.setVisibility(View.VISIBLE);
        navigationView.getMenu().findItem(R.id.nav_notificaciones).setChecked(true);
        ChangeFragment(NavigationFragment.Notificacion);

    }

    private void ClickMenuConfiguracion() {
        uncheckItemsMenu();
        //setTitle("Configuración");
        bottomNavigationView.setVisibility(View.VISIBLE);
        navigationView.getMenu().findItem(R.id.nav_configuracion).setChecked(true);
        ChangeFragment(NavigationFragment.Configuracion);
    }

    private void ClickMenuContactenos() {
        uncheckItemsMenu();
        //setTitle("Contáctenos");
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_Contactenos).setChecked(true);
        ChangeFragment(NavigationFragment.Contactenos);
    }

    private void ClickMenuRedesSociales() {
        uncheckItemsMenu();
        //setTitle("Redes Sociales");
        bottomNavigationView.setVisibility(View.VISIBLE);

        navigationView.getMenu().findItem(R.id.nav_RedesSociales).setChecked(true);
        ChangeFragment(NavigationFragment.RedesSociales);
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }

    public void uncheckItemsMenu() {
        // limpiamos todos los seleccionados
        Menu menu = navigationView.getMenu();
        uncheckItems(menu);
        menu = bottomNavigationView.getMenu();
        uncheckItems(menu);
    }

    private void uncheckItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    public void ChangeFragment(NavigationFragment value){
        Fragment fragment = null;
        switch (value) {
            case Inicio:    fragment = new FragmentInicio2(); break;
            case Noticias:    fragment = new FragmentNoticias(); break;
            case Comunicados: fragment = new FragmentComunicados(); break;
            case Imagenes: fragment = new FragmentImagenes(); break;
            case Videos: fragment = new FragmentVideos(); break;
            case Tickets: fragment = new FragmentTickets(); break;
            case Notificacion: fragment = new FragmentNotificacion(); break;
            case Configuracion: fragment = new FragmentConfiguracion(); break;
            case Contactenos: fragment = new FragmentContactenos(); break;
            case RedesSociales: fragment = new FragmentRedesSociales(); break;

        }
        if(fragment!=null&&GlobalVariables.fragmentStack.size()==0){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, fragment)
                    //.hide(GlobalVariables.fragmentStack.lastElement())
                    .commit();
                    Utils.apilarFrag(fragment);
       /* if (GlobalVariables.fragmentStack.size()<=1) {
            GlobalVariables.fragmentStack.push(fragment);
        }else{
            GlobalVariables.fragmentStack.pop();
            GlobalVariables.fragmentStack.push(fragment);
        }*/
        }else if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, fragment)
                    .hide(GlobalVariables.fragmentStack.lastElement())
                    .commit();
            Utils.apilarFrag(fragment);
        }

    }



    public String Recuperar_data() {

        SharedPreferences settings =  getSharedPreferences("datos", Context.MODE_PRIVATE);
        String url_servidor = settings.getString("url","");
        //Toast.makeText(this, url_servidor, Toast.LENGTH_SHORT).show();
        return url_servidor;
    }

}

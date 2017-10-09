package com.pango.comunicaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.lang.reflect.Field;

import layout.FragmentComunicados;
import layout.FragmentConfiguracion;
import layout.FragmentImagenes;
import layout.FragmentInicio;
import layout.FragmentNoticias;
import layout.FragmentNotificacion;
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
        FragmentConfiguracion.OnFragmentInteractionListener

{
  //  private int mSelectedItem;
  //  private static final String SELECTED_ITEM = "arg_selected_item";

    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private enum NavigationFragment{
        Inicio,
        Noticias,
        Comunicados,
        Imagenes,
        Videos,
        Tickets,
        Notificacion,
        Configuracion

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //GlobalVariables.Urlbase=Recuperar_data();
       // Intent toReservaTicketFiltro = new Intent(getApplicationContext(), ReservaTicketFiltro.class);
        //startActivity(toReservaTicketFiltro);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // ponemos el contenido inicial
        ChangeFragment(NavigationFragment.Inicio);
        uncheckItemsMenu();
        bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);

        //getSupportActionBar().

        setTitle("Antapaccay te informa");


    }


    @Override
    public void onBackPressed() {
        MenuItem homeItem=bottomNavigationView.getMenu().getItem(0);
        MenuItem noticiaItem = bottomNavigationView.getMenu().getItem(1);
        MenuItem comItem = bottomNavigationView.getMenu().getItem(2);
        MenuItem imagenItem = bottomNavigationView.getMenu().getItem(3);
        MenuItem videoItem = bottomNavigationView.getMenu().getItem(4);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        /*} else  if (mSelectedItem == videoItem.getItemId()||mSelectedItem == imagenItem.getItemId()) {

                bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

                //para variar la seleccion de items
                MenuItem menuItem = bottomNavigationView.getMenu().getItem(0);
                menuItem.setChecked(false);


                mSelectedItem=homeItem.getItemId();

                //setSelectedItemId (homeItem.getItemId());
                onNavigationItemSelected(homeItem);
*/
        }else {
            super.onBackPressed();
        }



            /*{

            super.onBackPressed();
        }*/
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
            bottomNavigationView.getMenu().findItem(R.id.navigation_publicaciones).setChecked(true);
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

        }else if (id == R.id.nav_notificaciones){
            ClickMenuNotificacion();
            uncheckItemsMenu();
        }else if (id == R.id.nav_configuracion){
            ClickMenuConfiguracion();
            uncheckItemsMenu();
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
                    setTitle("Antapaccay te informa");
                    ChangeFragment(NavigationFragment.Inicio);
                    uncheckItemsMenu();
                    return true;
                case R.id.navigation_imagenes:
                    //uncheckItemsMenu();
                    //navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(true);
                    ClickMenuImagenes();
                    return true;
                case R.id.navigation_videos:
                    ClickMenuVideos();
                    //uncheckItemsMenu();
                    //navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
                    return true;
                case R.id.navigation_noticias:
                    ClickMenuNoticias();
                    return true;
                case R.id.navigation_publicaciones:
                    //uncheckItemsMenu();
                    //navigationView.getMenu().findItem(R.id.nav_publicaciones).setChecked(true);
                    ClickMenuComunicados();
                    return true;
            }
            return false;
        }

    };

    public void ClickMenuNoticias() {
        uncheckItemsMenu();
        setTitle("Noticias");
        bottomNavigationView.getMenu().findItem(R.id.navigation_noticias).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_noticias).setChecked(true);
        ChangeFragment(NavigationFragment.Noticias);
    }

    public void ClickMenuComunicados() {

        uncheckItemsMenu();
        setTitle("Comunicados");

        bottomNavigationView.getMenu().findItem(R.id.navigation_publicaciones).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_publicaciones).setChecked(true);
        ChangeFragment(NavigationFragment.Comunicados);
    }
    public void ClickMenuImagenes() {
        uncheckItemsMenu();
        setTitle("Imágenes");

        bottomNavigationView.getMenu().findItem(R.id.navigation_imagenes).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(true);
        ChangeFragment(NavigationFragment.Imagenes);

    }
    public void ClickMenuVideos() {
        uncheckItemsMenu();
        setTitle("Videos");
        bottomNavigationView.getMenu().findItem(R.id.navigation_videos).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
        ChangeFragment(NavigationFragment.Videos);
    }
//opciones menu lateral
    private void ClickMenuTickets() {
        uncheckItemsMenu();
        setTitle("Reserva de Buses");
        navigationView.getMenu().findItem(R.id.nav_tickets).setChecked(true);
        ChangeFragment(NavigationFragment.Tickets);
    }

    private void ClickMenuNotificacion() {
        uncheckItemsMenu();
        setTitle("Notificaciones");
        navigationView.getMenu().findItem(R.id.nav_notificaciones).setChecked(true);
        ChangeFragment(NavigationFragment.Notificacion);

    }

    private void ClickMenuConfiguracion() {
        uncheckItemsMenu();
        setTitle("Configuración");
        navigationView.getMenu().findItem(R.id.nav_configuracion).setChecked(true);
        ChangeFragment(NavigationFragment.Configuracion);
    }

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

    private void ChangeFragment(NavigationFragment value){
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
        }
        if(fragment!=null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
    }



    public String Recuperar_data() {

        SharedPreferences settings =  getSharedPreferences("datos", Context.MODE_PRIVATE);
        String url_servidor = settings.getString("url","valorpordefecto");
        //Toast.makeText(this, url_servidor, Toast.LENGTH_SHORT).show();
        return url_servidor;
    }


}

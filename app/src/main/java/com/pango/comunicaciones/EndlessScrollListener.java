package com.pango.comunicaciones;

import android.widget.AbsListView;



public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int limiteVisible = 5;
    private int paginaActual = 0;
    private int cuentaTotalPrevia = 0;
    private boolean cargando = true;
    private int indicePaginaInicio = 0;

    public EndlessScrollListener() { }

    public EndlessScrollListener(int limiteVisible) {

        this.limiteVisible = limiteVisible;
    }

    public EndlessScrollListener(int limiteVisible, int paginaInicio) {
        this.limiteVisible = limiteVisible;
        this.paginaActual = paginaInicio;
        this.indicePaginaInicio = paginaInicio;
    }


    public abstract boolean onLoadMore(int page, int totalItemCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // no se tiene que modificar nada aca
       /* if (scrollState == SCROLL_STATE_IDLE) {
            GlobalVariables.isScrolling = false;
        }*/

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // lista es invalida y debe ser reseteada a su estado inicial
        //GlobalVariables.isScrolling = true;
        if (totalItemCount < cuentaTotalPrevia) {
            this.paginaActual = this.indicePaginaInicio;
            this.cuentaTotalPrevia = totalItemCount;
            if (totalItemCount == 0) { this.cargando = true; }
        }

        // si esta cargando y la cuenta total cambio actualizamos
        if (cargando && (totalItemCount > cuentaTotalPrevia)) {
            cargando = false;
            cuentaTotalPrevia = totalItemCount;
            paginaActual++;
        }

        if (!cargando && (firstVisibleItem + visibleItemCount + limiteVisible) >= totalItemCount) {
            cargando = onLoadMore(paginaActual + 1, totalItemCount);
        }
    }


}

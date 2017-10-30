package com.pango.comunicaciones.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.pango.comunicaciones.R;
import com.pango.comunicaciones.model.ComDet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 24/08/2017.
 */

public class DesComAdap extends ArrayAdapter<ComDet> {


    ImageButton button;
    DownloadManager downloadManager;
    private Context context;
    private List<ComDet> data = new ArrayList<ComDet>();

    //context, R.layout.listdescarga, data
    public DesComAdap(Context context, List<ComDet> data) {
        super(context, R.layout.comlistdes, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        //ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.comlistdes, null,true);



        ImageButton icono = (ImageButton) rowView.findViewById(R.id.com_icon_des);
        icono.setTag(position);

        TextView dNomFile = (TextView)  rowView.findViewById(R.id.com_nom_file);
        TextView dTamanio = (TextView)  rowView.findViewById(R.id.com_tam_file);
        TextView dTipo = (TextView)  rowView.findViewById(R.id.com_tipo_file);

        //asignando a las variables los valores obtenidos
        final String tempNombre=data.get(position).getNombre();
        final int tempTamanio=data.get(position).getTamanio();
        final String tempTipo=data.get(position).getTipo_arc();






            //cargar la data al layout//////
            dNomFile.setText(tempNombre);
            dTamanio.setText(tempTamanio + " Kb");
            dTipo.setText(tempTipo);


            icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(convertView, position, 0);


                    //Intent intent = new Intent();

                }
            });







        return rowView;
    }




}

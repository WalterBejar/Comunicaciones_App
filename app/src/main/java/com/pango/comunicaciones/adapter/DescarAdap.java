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
import com.pango.comunicaciones.model.NotDet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 18/08/2017.
 */

public class DescarAdap extends ArrayAdapter<NotDet> {
    ImageButton button;
    DownloadManager downloadManager;
    private Context context;
    private List<NotDet> data = new ArrayList<NotDet>();

    //context, R.layout.listdescarga, data
    public DescarAdap(Context context, List<NotDet> data) {
        super(context, R.layout.listdescarga, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        //ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.listdescarga, null,true);



            ImageButton icono = (ImageButton) rowView.findViewById(R.id.icon_des);
        icono.setTag(position);

        TextView dNomFile = (TextView)  rowView.findViewById(R.id.nom_file);
        TextView dTamanio = (TextView)  rowView.findViewById(R.id.tam_file);
        TextView dTipo = (TextView)  rowView.findViewById(R.id.tipo_file);

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
                }
            });



        return rowView;
    }

}

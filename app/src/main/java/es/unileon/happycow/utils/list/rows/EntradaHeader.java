package es.unileon.happycow.utils.list.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.unileon.happycow.R;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.Rows;

/**
 * Created by dorian on 6/05/15.
 */
public class EntradaHeader extends EntradaLista {

    public EntradaHeader(){
        super(Rows.HEADER);
    }

    @Override
    public View onView(LayoutInflater inflater, View view, ViewGroup viewGroup) {

        //si existe la vista, recojo el holder
        if(view==null) {
            view = inflater.inflate(R.layout.entrada_header, viewGroup, false);
        }

        return view;
    }

    @Override
    public View onViewExpandable(LayoutInflater inflater, View view, ViewGroup viewGroup, boolean isExpand) {
        return onView(inflater, view, viewGroup);
    }
}

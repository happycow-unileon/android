package es.unileon.happycow.utils.list.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.unileon.happycow.R;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.Rows;
import es.unileon.happycow.utils.list.holders.CriterionHolder;

/**
 * Created by dorian on 19/05/15.
 */
public class EntradaCriterion extends EntradaLista{
    private String name;

    public EntradaCriterion(String name){
        super(Rows.CRITERION);
        this.name=name;
    }

    @Override
    public View onView(LayoutInflater inflater, View view, ViewGroup parent) {

        CriterionHolder holder=null;

        //si existe la vista, recojo el holder
        if(view!=null){
            holder=(CriterionHolder)view.getTag();
        }

        //si no hay holder, lo creo
        if(holder==null) {
            holder = new CriterionHolder();
            view = inflater.inflate(R.layout.entrada_criterion, parent, false);

            holder.criterion = (TextView) view.findViewById(R.id.criterion);

            view.setTag(holder);
        }

        //relleno los datos de la fila
        holder.criterion.setText(name);

        return view;
    }

    @Override
    public View onViewExpandable(LayoutInflater inflater, View view, ViewGroup viewGroup, boolean isExpand) {
        return onView(inflater, view, viewGroup);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof EntradaCriterion){
            String otherName=((EntradaCriterion)o).name;
            return otherName.compareTo(name)==0;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

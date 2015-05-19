package es.unileon.happycow.utils.list.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.unileon.happycow.R;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.Rows;
import es.unileon.happycow.utils.list.holders.CriterionHolder;
import es.unileon.happycow.utils.list.holders.ValorationHolder;

/**
 * Created by dorian on 19/05/15.
 */
public class EntradaValoration extends EntradaLista {
    private IdHandler criterion;
    private Valoration valoration;

    public EntradaValoration(Valoration valoration, IdHandler criterion){
        super(Rows.VALORATION);
        this.valoration=valoration;
        this.criterion=criterion;
    }

    public EntradaValoration(Valoration valoration, String criterion){
        this(valoration, new IdCriterion(criterion));
    }

    public EntradaValoration(Float valoration, IdHandler criterion){
        this(new Valoration(valoration), criterion);
    }

    public EntradaValoration(Float valoration, String criterion){
        this(new Valoration(valoration), new IdCriterion(criterion));
    }

    @Override
    public View onView(LayoutInflater inflater, View view, ViewGroup parent) {

        ValorationHolder holder=null;

        //si existe la vista, recojo el holder
        if(view!=null){
            holder=(ValorationHolder)view.getTag();
        }

        //si no hay holder, lo creo
        if(holder==null) {
            holder = new ValorationHolder();
            view = inflater.inflate(R.layout.entrada_valoration, parent, false);

            holder.valoration = (TextView) view.findViewById(R.id.valoration);

            view.setTag(holder);
        }

        //relleno los datos de la fila
        holder.valoration.setText("Valoración: "+valoration.getNota());

        return view;
    }

    @Override
    public View onViewExpandable(LayoutInflater inflater, View view, ViewGroup viewGroup, boolean isExpand) {
        return onView(inflater, view, viewGroup);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof EntradaCriterion){
            EntradaValoration other=(EntradaValoration)o;
            Float nota=other.valoration.getNota();
            boolean result=nota==valoration.getNota();
//            result=result & other.criterion.compareTo(criterion)==0;
            return result;
        }else{
            return false;
        }
//        return false;
    }

    @Override
    public int hashCode() {
        Float note=valoration.getNota();
        return note.hashCode();
    }
}

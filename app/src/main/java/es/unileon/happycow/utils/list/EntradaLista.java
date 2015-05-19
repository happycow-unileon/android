package es.unileon.happycow.utils.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

/**
 * Clase base para las filas de una lista, se instancia y sobreescribe el método onView
 * (ejemplo en EntradaExcel)
 * Created by dorian on 24/04/15.
 */
public abstract class EntradaLista {
    private static long idEntradaLista=0;
    private long id;
    private Rows typeRow;

    public EntradaLista(Rows typeRow){
        id=idEntradaLista;
        idEntradaLista++;
        this.typeRow=typeRow;
    }

    public Rows getTypeRow(){
        return typeRow;
    }

    public abstract View onView(LayoutInflater inflater, View view, ViewGroup viewGroup);
    public abstract View onViewExpandable(LayoutInflater inflater, View view, ViewGroup viewGroup, boolean isExpand);


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        // Start with a non-zero constant.
        int result = 17;

        // Include a hash for each field.
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}

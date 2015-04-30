package es.unileon.happycow.utils.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Clase base para las filas de una lista, se instancia y sobreescribe el método onView
 * (ejemplo en EntradaExcel)
 * Created by dorian on 24/04/15.
 */
public abstract class EntradaLista {
    public abstract View onView(LayoutInflater inflater, View view, ViewGroup viewGroup);
}

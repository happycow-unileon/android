package es.unileon.happycow.utils.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Lista genérica que puede contener cualquier lista de EntradaLista (heredable)
 * Created by dorian on 24/04/15.
 */
public class ListAdapter extends BaseAdapter {
    private static final int numberRows = Rows.values().length;
    private List<EntradaLista> list;
    private LayoutInflater inflater;

    public ListAdapter(Context context, List<EntradaLista> list) {
        super();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return numberRows;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getTypeRow().ordinal();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return list.get(i).onView(inflater, view, viewGroup);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}

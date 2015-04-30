package es.unileon.happycow.utils.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import es.unileon.happycow.R;

/**
 * Ejemplo de fila. Para cada fila se necesita un xml para el aspecto de la fila
 * y un holder (ejemplo ExcelHolder)
 * Created by dorian on 24/04/15.
 */
public class EntradaExcel  extends EntradaLista{
    public boolean selected;
    public String name;

    public EntradaExcel(String name, boolean selected) {
        this.selected = selected;
        this.name = name;
    }

    @Override
    public View onView(LayoutInflater inflater, View view, ViewGroup viewGroup) {
        ExcelHolder holder=null;

        //si existe la vista, recojo el holder
        if(view!=null){
            holder=(ExcelHolder)view.getTag();
        }

        //si no hay holder, lo creo
        if(holder==null){
            holder=new ExcelHolder();
            view = inflater.inflate(R.layout.entrada_excel, viewGroup, false);

            //pillo los elementos de la vista y los guardo en el holder que queda guardado en el view
            holder.name=(TextView) view.findViewById(R.id.nameGranja);
            holder.selected=(CheckBox) view.findViewById(R.id.checkBox);
            holder.selected.setText("");

            //si la vista tiene elementos clicables, hay que dejar el clikable y focusable a false y sobreescribir
            //este método (aunque se deje vacío para que tenga el comportamiento por defecto, que es
            //seleccionar/deseleccionar el check)
            holder.selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    CheckBox check=(CheckBox)view;
//                    check.setChecked(!check.isChecked());
                }
            });

            view.setTag(holder);
        }

        //relleno los datos de la fila
        holder.name.setText(name);
        holder.selected.setChecked(selected);

        return view;
    }
}

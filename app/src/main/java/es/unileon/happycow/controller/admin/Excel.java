package es.unileon.happycow.controller.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.unileon.happycow.R;
import es.unileon.happycow.utils.list.EntradaExcel;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.ListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Excel extends Fragment {


    public Excel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot= inflater.inflate(R.layout.fragment_excel, container, false);

        ListView lista = (ListView) viewRoot.findViewById(R.id.listaExcel);
        ArrayList<EntradaLista> list=new ArrayList<>();

        //relleno lista
        EntradaLista entrada=new EntradaExcel("hola", true);
        list.add(entrada);
        entrada=new EntradaExcel("pepito", true);
        list.add(entrada);
        entrada=new EntradaExcel("grillo", true);
        list.add(entrada);
        entrada=new EntradaExcel("probando", false);
        list.add(entrada);
        entrada=new EntradaExcel("listas", false);
        list.add(entrada);
        entrada=new EntradaExcel("con", false);
        list.add(entrada);
        entrada=new EntradaExcel("multi", true);
        list.add(entrada);
        entrada=new EntradaExcel("row", true);
        list.add(entrada);
        entrada=new EntradaExcel("woww!!", false);
        list.add(entrada);

        lista.setAdapter(new ListAdapter(this.getActivity(), list));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EntradaLista elegido = (EntradaLista) adapterView.getItemAtPosition(i);

                CharSequence texto = "Seleccionado: ";
                Toast toast = Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        return viewRoot;
    }


}

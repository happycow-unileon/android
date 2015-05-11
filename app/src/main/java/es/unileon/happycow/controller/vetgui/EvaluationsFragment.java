package es.unileon.happycow.controller.vetgui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.ListAdapter;
import es.unileon.happycow.utils.list.rows.EntradaCardFarm;
import es.unileon.happycow.utils.list.rows.EntradaHeader;

public class EvaluationsFragment extends Fragment {

    private ListView listViewEvaluations;
    private ListAdapter adapter;
    private List<EntradaLista> evaluationsList = new ArrayList<>();
    private Farm farm;

    public EvaluationsFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evaluations, container, false);
        listViewEvaluations= (ListView) rootView.findViewById(R.id.listViewEvaluations);

        evaluationsList.add(new EntradaCardFarm(farm));
        adapter=new ListAdapter(getActivity(), evaluationsList);
        listViewEvaluations.setAdapter(adapter);



        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

}

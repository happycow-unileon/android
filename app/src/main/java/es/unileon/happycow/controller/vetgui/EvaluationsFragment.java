package es.unileon.happycow.controller.vetgui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import  java.util.Date;

import es.unileon.happycow.R;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.utils.list.EntradaLista;
import es.unileon.happycow.utils.list.ListAdapter;
import es.unileon.happycow.utils.list.rows.EntradaCardEvaluation;
import es.unileon.happycow.utils.list.rows.EntradaCardFarm;

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


        for(int i=0; i<10; i++){
            evaluationsList.add(new EntradaCardEvaluation(new InformationEvaluation(null, null, null,
                    4.5f, 5.6f, 7.7f, 9.8f, 6.7f, new Date(), i)));
        }

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

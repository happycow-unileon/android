package es.unileon.happycow.controller.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.model.Rol;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.composite.Criterion;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteCriterion extends Fragment {


    public DeleteCriterion() {
        // Required empty public constructor
    }

    private void deleteCriterion(View view){
        Spinner spinner=(Spinner)view.findViewById(R.id.listCriterions);
        String target=spinner.getSelectedItem().toString();
        boolean resultado=Database.getInstance(null).removeCriterion(new IdCriterion(target));
        if(resultado){
            addCriterions(view);
        }else{
            TextView text=(TextView)view.findViewById(R.id.error);
            text.setText("Error eliminando el criterio");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_delete_criterion, container, false);
        Button button = (Button) rootView.findViewById(R.id.buttonDeleteCriterion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCriterion(rootView);
            }
        });

        addCriterions(rootView);

        return rootView;
    }

    private void addCriterions(View view){
        Spinner spinner=(Spinner)view.findViewById(R.id.listCriterions);
        LinkedList<Criterion> list= Database.getInstance(null).getListCriterion();
        LinkedList<String> listCriterions=new LinkedList<>();
        for (Criterion criterion:list){
            listCriterions.add(criterion.getName());
        }
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listCriterions));
    }


}

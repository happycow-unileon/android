package es.unileon.happycow.controller.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.composite.Criterion;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCriterion extends Fragment {


    public NewCriterion() {
        // Required empty public constructor
    }

    private void addCriterion(View view){
        TextView error=(TextView)view.findViewById(R.id.error);

        EditText text=(EditText)view.findViewById(R.id.nameCriterion);
        String name=text.getText().toString();
        IdHandler id=new IdCriterion(name);

        if(Database.getInstance(null).getCriterion(id)!=null){
            error.setText("Ya existe un criterio con ese nombre.");
        }else{
            Spinner spinner=(Spinner)view.findViewById(R.id.category);
            String category=spinner.getSelectedItem().toString();

            text=(EditText)view.findViewById(R.id.description);
            String description=text.getText().toString();

            text=(EditText)view.findViewById(R.id.ayuda);
            String ayuda=text.getText().toString();

            Criterion criterion=new Criterion(id,
                    new IdCategory(category),
                    description, ayuda, 1);
            if(Database.getInstance(null).newCriterion(criterion)){
                error.setText("Criterio correctamente añadido.");
            }else{
                error.setText("Problemas al añadir el criterio, pruebe otra vez.");
            }
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_new_criterion, container, false);
        Button button = (Button) rootView.findViewById(R.id.buttonNewCriterion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCriterion(rootView);
            }
        });

        Spinner spinner=(Spinner)rootView.findViewById(R.id.category);
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Category.values()));


        return rootView;
    }


}

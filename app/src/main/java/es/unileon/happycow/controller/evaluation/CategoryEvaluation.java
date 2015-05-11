package es.unileon.happycow.controller.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;

import es.unileon.happycow.R;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;

/**
 * Created by dorian on 11/05/15.
 */
public class CategoryEvaluation extends Fragment {
    private InterfaceEvaluationModel model;
    private Category category;
    private TextView valoration;
    private Spinner criterions;

    public CategoryEvaluation() {

    }


    public InterfaceEvaluationModel getModel() {
        return model;
    }

    public void setModel(InterfaceEvaluationModel model) {
        this.model = model;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addValoration(View view, float valorationValue) {
        String nombre = (String)criterions.getSelectedItem();
        if (nombre != null) {
            IdHandler criterion = new IdCriterion(nombre);
            IdHandler categoryId = new IdCategory(category);
            Valoration val = new Valoration(valorationValue);
            model.add(categoryId, criterion, val);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_category_evaluation, container, false);
        Button button = (Button) rootView.findViewById(R.id.addValoration);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float valorationValue=Float.parseFloat(valoration.getText().toString());
                addValoration(rootView, valorationValue);
            }
        });

        valoration=(TextView)rootView.findViewById(R.id.valorationValue);

        criterions=(Spinner)rootView.findViewById(R.id.spinnerCriterion);
        criterions.setAdapter(new ArrayAdapter<Criterion>(getActivity(), android.R.layout.simple_list_item_1,
                Database.getInstance(null).getListCriterion(category)));

        return rootView;
    }
}

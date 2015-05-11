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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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
import es.unileon.happycow.strategy.EvaluationAlgorithm;

/**
 * Created by dorian on 11/05/15.
 */
public class CategoryEvaluation extends Fragment {
    private InterfaceEvaluationModel model;
    private Category category;
    private TextView valoration;
    private Spinner criterions;
    private EditText ponderationCategory;
    private EditText ponderationCriterion;

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
            addCriterion(nombre);
            IdHandler criterion = new IdCriterion(nombre);
            IdHandler categoryId = new IdCategory(category);
            Valoration val = new Valoration(valorationValue);
            model.add(categoryId, criterion, val);
        }
    }

    public void addCriterion(String criterion) {
        IdHandler categoryId = new IdCategory(category);
        Criterion cri = Database.getInstance(null).getCriterion(new IdCriterion(criterion));
        model.add(categoryId, cri);
    }

    public void setPonderationCategory(float ponderation) {
        model.setWeighing(new IdCategory(category), ponderation);
        ponderationCategory.setText(String.valueOf(ponderation));
    }

    public void setPonderationCriterion(float ponderation) {
        IdHandler criterion = new IdCriterion((String)criterions.getSelectedItem());
        model.setWeighing(criterion, ponderation);
        ponderationCriterion.setText(String.valueOf(ponderation));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_category_evaluation, container, false);

        valoration=(TextView)rootView.findViewById(R.id.valorationValue);
        ponderationCategory=(EditText)rootView.findViewById(R.id.ponderationCategory);
        ponderationCriterion=(EditText)rootView.findViewById(R.id.ponderationCriterion);

        int cows = EvaluationAlgorithm.necesaryNumberOfCows(50);//model.getInformation().getNumberCows());
        TextView cowsNumber=(TextView) rootView.findViewById(R.id.numberCows);
        cowsNumber.setText("Se necesitan mínimo "+String.valueOf(cows)+" vacas");

        setComboCriterion(rootView);
        setButtons(rootView);

        return rootView;
    }

    private void setComboCriterion(View rootView){
        criterions=(Spinner)rootView.findViewById(R.id.spinnerCriterion);

        LinkedList<Criterion> list=Database.getInstance(null).getListCriterion(category);
        LinkedList<String> listCriterion=new LinkedList<>();
        for(Criterion cri : list){
            listCriterion.add(cri.getName());
        }
        criterions.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                listCriterion));
    }

    private void setButtons(final View rootView){
        Button button = (Button) rootView.findViewById(R.id.addValoration);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float valorationValue=Float.parseFloat(valoration.getText().toString());
                addValoration(rootView, valorationValue);
            }
        });

        button=(Button)rootView.findViewById(R.id.buttonPonderarCategory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float value=Float.parseFloat(ponderationCategory.getText().toString());
                setPonderationCategory(value);
            }
        });

        button=(Button)rootView.findViewById(R.id.buttonPonderarCriterion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float value=Float.parseFloat(ponderationCriterion.getText().toString());
                setPonderationCriterion(value);
            }
        });

        ImageButton button2;
        button2=(ImageButton)rootView.findViewById(R.id.lessCore);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeValoration(false);
            }
        });

        button2=(ImageButton)rootView.findViewById(R.id.moreCore);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeValoration(true);
            }
        });
    }

    private void changeValoration(boolean more){
        int valorationActual=Integer.parseInt(valoration.getText().toString());
        if(more && valorationActual<5){
            valorationActual++;
        }else if(!more && valorationActual>1){
            valorationActual--;
        }
        valoration.setText(String.valueOf(valorationActual));
    }

}

package es.unileon.happycow.controller.admin;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import es.unileon.happycow.R;
import es.unileon.happycow.controller.Evaluation;
import es.unileon.happycow.handler.Category;

/**
 * Created by dorian on 1/05/15.
 */
public class ManagerPanelEvaluation {
    View view;
    Evaluation controller;

    Spinner category;
    Spinner criterion;
    EditText ponderationCategory;
    EditText ponderationCriterion;
    Button addValoration;

    TextView valorationText;
    int valoration;

    ExpandableListView list;

    public ManagerPanelEvaluation(View view, Evaluation controller){
        this.view=view;
        this.controller=controller;

        category=(Spinner)view.findViewById(R.id.spinnerCategory);
        criterion=(Spinner)view.findViewById(R.id.spinnerCriterion);

        ponderationCategory=(EditText)view.findViewById(R.id.ponderationCategory);
        ponderationCriterion=(EditText)view.findViewById(R.id.ponderationCriterion);

        addValoration=(Button)view.findViewById(R.id.addValoration);

        list=(ExpandableListView)view.findViewById(R.id.valorations);

//        setComboCategory();
//        addEvents();
    }


//    public void setNumberCow(int number){
//        TextView lblNumberCows=(TextView)view.findViewById(R.id.numberCows);
//        lblNumberCows.setText("Se necesitan " + String.valueOf(number) + " vacas.");
//    }
//
//    public void setComboCriterion(LinkedList<String> list){
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, list);
//        criterion.setAdapter(adapter);
//    }
//
//    public String getCriterion(){
//        if(criterion.getSelectedItemPosition()>=0) {
//            return (String) criterion.getSelectedItem();
//        }else{
//            return "";
//        }
//    }
//
//    private void evaluacionTerminada(){
//        controller.saveValoration();
//    }
//
//    public String getValoration(){
//        return String.valueOf(valoration);
//    }
//
//    private void setComboCategory(){
//        ArrayList<String> list=new ArrayList<>();
//        for (String string : Category.getArrayString()) {
//            list.add(string);
//        }
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, list);
//        category.setAdapter(adapter);
//        category.setSelection(0);
//    }
//
//    private void addEvents(){
//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //after text changed
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                textPonderationCategoryActionPerformed();
//            }
//        };
//
//        ponderationCategory.addTextChangedListener(textWatcher);
//
//        textWatcher=new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                textPonderationCriterionActionPerformed();
//            }
//        };
//
//        ponderationCriterion.addTextChangedListener(textWatcher);
//
//        ImageButton button=(ImageButton)view.findViewById(R.id.help);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buttonHelpActionPerformed();
//            }
//        });
//
//        Button button2=(Button)view.findViewById(R.id.addValoration);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buttonValorateActionPerformed();
//            }
//        });
//
//        button2=(Button)view.findViewById(R.id.buttonDone);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                evaluacionTerminada();
//            }
//        });
//
//        button=(ImageButton)view.findViewById(R.id.lessCore);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                valoration--;
//                if(valoration<0){
//                    valoration=0;
//                }
//                valorationText.setText(String.valueOf(valoration));
//            }
//        });
//
//        button=(ImageButton)view.findViewById(R.id.moreCore);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                valoration++;
//                if(valoration>5){
//                    valoration=5;
//                }
//                valorationText.setText(String.valueOf(valoration));
//            }
//        });
//
//    }
//
////    @Override
////    public void addValoration(String valoration) {
////        getActualPanel().addValoration(valoration);
////    }
////
////    @Override
////    public void addCriterion(String criterion) {
////        getActualPanel().addCriterion(criterion);
////    }
////
////    @Override
////    public void removeCriterion(String criterion) {
////        getActualPanel().removeCriterion(criterion);
////    }
////
////    @Override
////    public void removeValorations() {
////        getActualPanel().removeValorations();
////    }
//
//    public String getSelectedCriterion(){
//        return (String)criterion.getSelectedItem();
//    }
//
//    public Category getSelectedCategory(){
//        return Category.getEnum((String) category.getSelectedItem());
//    }
//
//    public void setPonderationCategory(Float ponderation){
//        ponderationCategory.setText(Float.toString(ponderation));
//    }
//
//    public void setPonderationCriterion(Float ponderation){
//        ponderationCriterion.setText(Float.toString(ponderation));
//    }
//
//    public void habilitarValoraciones(){
//        addValoration.setEnabled(true);
//    }
//
//    public void deshabilitarValoraciones(){
//        addValoration.setEnabled(false);
//    }
//
//    private void buttonValorateActionPerformed() {
//        controller.addValoration(valoration);
//    }
//
//    private void textPonderationCategoryActionPerformed() {
//        controller.setPonderationCategory(Float.parseFloat(ponderationCategory.getText().toString()));
//    }
//
//    private void buttonHelpActionPerformed(){
//
//    }
//
//    private void textPonderationCriterionActionPerformed() {
//        controller.setPonderationCriterion(Float.parseFloat(ponderationCriterion.getText().toString()));
//    }

}

package es.unileon.happycow.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Iterator;
import java.util.LinkedList;

import es.unileon.happycow.R;
import es.unileon.happycow.controller.admin.ManagerPanelEvaluation;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.handler.IdFarm;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.EvaluationModel;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import es.unileon.happycow.procedures.Report;
import es.unileon.happycow.strategy.EvaluationAlgorithm;

/**
 * Created by dorian on 1/05/15.
 */
public class Evaluation extends Activity {
    private InterfaceEvaluationModel model;
    private boolean newEvaluation;
    private ManagerPanelEvaluation panel;
    private LinkedList<String> modelComboFood = new LinkedList<>();
    private LinkedList<String> modelComboHealth = new LinkedList<>();
    private LinkedList<String> modelComboBehaviour = new LinkedList<>();
    private LinkedList<String> modelComboHouse = new LinkedList<>();

//    public Evaluation(InterfaceEvaluationModel model){
//        this.model=model;
//        newEvaluation=false;
////        if(model!=null){
////            addAll();
////        }
//    }
//
//    public Evaluation(IdHandler farm){
//        this.model = new EvaluationModel(true, farm);
//        newEvaluation=true;
//    }
//
    private void common(){
        panel=new ManagerPanelEvaluation(findViewById(android.R.id.content), this);

//        setComboCriterion();
//        setNumberCows();
//        panel.deshabilitarValoraciones();
//        configurePanel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_criterion);


        Intent intent = getIntent();
        newEvaluation=intent.getBooleanExtra("newEvaluation", false);
        int farm = intent.getIntExtra("IdFarm",0);
        String idEvaluation=intent.getStringExtra("IdEvaluation");

        if(newEvaluation) {
            this.model = new EvaluationModel(newEvaluation, new IdFarm(farm));
        }
    }

//    private void addAll(){
//        for (Category category : Category.values()) {
//            LinkedList<String> criterios=new LinkedList<>();
//            for (Criterion criterion : model.getListCriterion(category)) {
//                criterios.add(criterion.getName());
//            }
////            panel.setCriterion(category, criterios);
//        }
//    }
//
//    private void configurePanel(){
//        panel.setPonderationCategory(model.getWeighing(new IdCategory(panel.getSelectedCategory())));
//    }
//
//    private void setNumberCows() {
////        Farm farm = Database.getInstance(null).getFarm(model.getInformation().getIdFarm());
//        int cows = EvaluationAlgorithm.necesaryNumberOfCows(model.getInformation().getNumberCows());
//        panel.setNumberCow(cows);
//    }
//
//    private void setComboCriterion() {
//        LinkedList<Criterion> lista = Database.getInstance(null).getListCriterion();
//        for (Criterion criterion : lista) {
//            IdCategory id = (IdCategory) criterion.getCategory();
//            switch (id.getCategory()) {
//                case FOOD:
//                    modelComboFood.add(criterion.getName());
//                    break;
//                case HEALTH:
//                    modelComboHealth.add(criterion.getName());
//                    break;
//                case HOUSE:
//                    modelComboHouse.add(criterion.getName());
//                    break;
//                case BEHAVIOUR:
//                    modelComboBehaviour.add(criterion.getName());
//                    break;
//            }
//        }
//    }
//
//    public void addValoration(float valoration) {
//        String nombre = panel.getCriterion();
//        if (nombre != null) {
//            IdHandler criterion = new IdCriterion(nombre);
//            IdHandler category = new IdCategory(panel.getSelectedCategory());
//            Valoration val = new Valoration(valoration);
//            model.add(category, criterion, val);
//        }
//    }
//
//    public void addCriterion(String criterion) {
//        IdHandler category = new IdCategory(panel.getSelectedCategory());
//        Criterion cri = Database.getInstance(null).getCriterion(new IdCriterion(criterion));
//        model.add(category, cri);
////        panel.addCriterion(criterion);
//    }
//
//    public void removeCriterion(String criterion) {
//        model.remove(new IdCriterion(criterion));
////        panel.removeValorations();
//        panel.deshabilitarValoraciones();
//    }
//
////    public void removeValoration(String valoration) {
////        String cortes[]=valoration.split(":");
////        float nota=Integer.parseInt(cortes[cortes.length-1].trim());
////        LinkedList<Valoration> val=model.listOfCriterion(new IdCriterion(panel.getCriterion()));
////        IdHandler idValoration=null;
////        for (Iterator<Valoration> it = val.iterator(); it.hasNext() && idValoration==null;) {
////            Valoration valoration1 = it.next();
////            if(valoration1.getNota()==nota){
////                idValoration=valoration1.getId();
////            }
////        }
////        if(idValoration!=null){
////            model.remove(idValoration);
////            IdCriterion idCri = new IdCriterion(panel.getCriterion());
////            int i = 1;
////            DefaultListModel list = new DefaultListModel();
////            for (Valoration valo : model.listOfCriterion(idCri)) {
////                String element = "Vaca " + String.valueOf(i) + " - Valoración: " + String.valueOf((int) valo.getNota());
////                list.addElement(element);
////                i++;
////            }
////            panel.setModelValoration(list);
////        }
////    }
//
//    public void changeCategory() {
//        IdHandler category = new IdCategory(panel.getSelectedCategory());
//        panel.setPonderationCategory(model.getWeighing(category));
//        if(panel.getCriterion()==null){
//            panel.deshabilitarValoraciones();
//        }
//    }
//
//    public void setPonderationCategory(float ponderation) {
//        IdHandler category = new IdCategory(panel.getSelectedCategory());
//        model.setWeighing(category, ponderation);
//        panel.setPonderationCategory(ponderation);
//    }
//
//    public void setPonderationCriterion(float ponderation) {
//        IdHandler criterion = new IdCriterion(panel.getCriterion());
//        model.setWeighing(criterion, ponderation);
//        panel.setPonderationCriterion(ponderation);
//    }
//
//    public void saveValoration() {
//        if (newEvaluation) {
//            Database.getInstance(null).saveEvaluation(model);
//        } else {
//            Database.getInstance(null).saveModifiedEvaluation(model);
//        }
//        Intent intent=new Intent(this, es.unileon.happycow.controller.Report.class);
////        intent.putExtra("report", new Report(model));
//        intent.putExtra("idFarm", model.getInformation().getIdFarm());
//    }
}

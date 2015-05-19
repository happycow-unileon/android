package es.unileon.happycow.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import java.util.Iterator;
import java.util.LinkedList;

import es.unileon.happycow.R;
import es.unileon.happycow.controller.admin.ManagerPanelEvaluation;
import es.unileon.happycow.controller.admin.TabsPagerAdapter;
import es.unileon.happycow.controller.evaluation.CategoryEvaluation;
import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.handler.IdCriterion;
import es.unileon.happycow.handler.IdEvaluation;
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
public class Evaluation extends FragmentActivity implements ActionBar.TabListener {
    private InterfaceEvaluationModel model;
    private boolean newEvaluation;
    private ManagerPanelEvaluation panel;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

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
        //panel=new ManagerPanelEvaluation(findViewById(android.R.id.content), this);

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
        if(newEvaluation) {
            System.out.println("Por nueva evaluacion");
            this.model = new EvaluationModel(newEvaluation, new IdFarm(farm));
        }else{
            System.out.println("Por evaluacion existente");
            int id=intent.getIntExtra("IdEvaluation",-1);
            IdHandler idEvaluation=new IdEvaluation(id);
            this.model=Database.getInstance(null).getEvaluation(idEvaluation);
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);


        CategoryEvaluation cat=new CategoryEvaluation();
        cat.setCategory(Category.FOOD);
        cat.setModel(model);
        mAdapter.addPage(cat, Category.getName(Category.FOOD));

        cat=new CategoryEvaluation();
        cat.setCategory(Category.HEALTH);
        cat.setModel(model);
        mAdapter.addPage(cat, Category.getName(Category.HEALTH));

        cat=new CategoryEvaluation();
        cat.setCategory(Category.BEHAVIOUR);
        cat.setModel(model);
        mAdapter.addPage(cat, Category.getName(Category.BEHAVIOUR));

        cat=new CategoryEvaluation();
        cat.setCategory(Category.HOUSE);
        cat.setModel(model);
        mAdapter.addPage(cat, Category.getName(Category.HOUSE));
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

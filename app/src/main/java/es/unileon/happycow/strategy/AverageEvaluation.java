
package es.unileon.happycow.strategy;

import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import java.util.LinkedList;

/**
 *
 * @author amdiaz8
 */
public class AverageEvaluation extends EvaluationAlgorithm{
    private float bestCategoryResult;
    private Category bestCategory;
    private float worstCategoryResult;
    private Category worstCategory;
    
    
    /**
     * 
     * @param model 
     */
    public AverageEvaluation(InterfaceEvaluationModel model) {
        super(model);
    }
    
    @Override
    public String toString(){
        this.calcular();
        StringBuilder result=new StringBuilder();
        result.append("Reporte\n");
        result.append(information());
        result.append(lineHeader());
        result.append(resumeEvaluation());
        result.append(lineHeader());
        result.append(alerts());
        result.append(lineHeader());
        result.append(evaluationDetails());
        return result.toString();
    }
    
    private String evaluationDetails(){
        StringBuilder result=new StringBuilder();
        result.append("Resultados detallados<br>");
        result.append("Mejor Categoria: ");
        result.append(bestCategory.toString());
        result.append(" ");
        result.append(bestCategoryResult);
        result.append("&#09;");
        
        result.append("Peor Categoría: ");
        result.append(worstCategory.toString());
        result.append(" ");
        result.append(worstCategoryResult);
        result.append("<br><br>");
        
        for (Category category : Category.values()) {
            result.append(category.toString());
            result.append("<br>");
            result.append("Criterio mejor evaluado: ");
            result.append(bestCriterion(category));
            result.append("&#09;");
            
            result.append("Criterio peor evaluado: ");
            result.append(worstCriterion(category));
            result.append("<br><br>");
        }
        
        
        return result.toString();
    }
    
    private String alerts(){
        return "Alerts under construction<br>";
    }
    
    private String information(){
        StringBuilder result=new StringBuilder();
        IdHandler idFarm=this.model.getInformation().getIdFarm();
        Farm farm=Database.getInstance(null).getFarm(idFarm);
        result.append(farm.getInformation());
        result.append(this.model.getInformation().getFecha().toString());
        result.append("<br>");
        result.append("Número de vacas en la evaluación: ");
        result.append(this.model.getInformation().getNumberCows());
        result.append("<br>");
        return result.toString();
    }
    
    private String resumeEvaluation(){
        StringBuilder text=new StringBuilder();
        text.append("Resultados por promedio<br>");
        
        text.append("Total: ");
        text.append(this.total);
        text.append("<br>");
        
        text.append("Categoría Alimentación: ");
        text.append(this.food);
        text.append("<br>");
        
        text.append("Categoría Salud: ");
        text.append(this.health);
        text.append("<br>");
        
        text.append("Categoría Hogar: ");
        text.append(this.house);
        text.append("<br>");
        
        text.append("Categoría Comportamiento: ");
        text.append(this.behaviour);
        text.append("<br>");
        
        return text.toString();
    }
    
    private String lineHeader(){
        return "<br><hr><br>";
    }

    private String bestCriterion(Category category){
        String nameCriterion="";
        float result=0;
        for (Criterion actualCriterion : model.getListCriterion(category)) {
            float criterion=0;
            LinkedList<Valoration> list=model.listOfCriterion(actualCriterion.getId());
            for (Valoration valoration : list) {
                criterion+=valoration.getNota();
            }
            criterion=criterion/(list.size());
            if(result<criterion){
                result=criterion;
                nameCriterion=actualCriterion.getName();
            }
        }
        return nameCriterion;
    }
    
    private String worstCriterion(Category category){
        String nameCriterion="";
        float result=Float.MAX_VALUE;
        for (Criterion actualCriterion : model.getListCriterion(category)) {
            float criterion=0;
            LinkedList<Valoration> list=model.listOfCriterion(actualCriterion.getId());
            for (Valoration valoration : list) {
                criterion+=valoration.getNota();
            }
            criterion=criterion/(list.size());
            if(result>criterion){
                result=criterion;
                nameCriterion=actualCriterion.getName();
            }
        }
        return nameCriterion;
    }
    
    private float nota(Category cat){
        float media=0;
        int conteo=0;
        LinkedList<Valoration> valorations=model.listOfCategory(cat);
        for (Valoration valoration : valorations) {
            media+=valoration.getWeighing()*valoration.getNota();
            conteo++;
        }
        
        float result;
        if(conteo==0){
            result=0;
        }else{
            result=media/conteo;
        }
        
        switch(cat){
            case BEHAVIOUR:
                behaviour=result;
                break;
            case FOOD:
                food=result;
                break;
            case HEALTH:
                health=result;
                break;
            case HOUSE:
                house=result;
                break;
        }
        
        return result;
    }
    
    private void getBestCategory(){
        Category best=Category.FOOD;
        float result=this.food;
        
        if(this.health>result){
            best=Category.HEALTH;
            result=this.health;
        }
        
        if(this.house>result){
            best=Category.HOUSE;
            result=this.house;
        }
        
        if(this.behaviour>result){
            best=Category.BEHAVIOUR;
            result=this.behaviour;
        }
        
        bestCategory=best;
        bestCategoryResult=result;
    }
    
    private void getWorstCategory(){
        Category worst=Category.FOOD;
        float result=this.food;
        
        if(this.health<result){
            worst=Category.HEALTH;
            result=this.health;
        } else {
        }
        
        if(this.house<result){
            worst=Category.HOUSE;
            result=this.house;
        }
        
        if(this.behaviour<result){
            worst=Category.BEHAVIOUR;
            result=this.behaviour;
        }
        
        worstCategory=worst;
        worstCategoryResult=result;
    }
    
    @Override
    public void calcular() {
        super.calcular();
        getBestCategory();
        getWorstCategory();
    }
    
}

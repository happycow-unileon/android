package es.unileon.happycow.strategy;

import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdCategory;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import java.util.LinkedList;

/**
 *
 * @author amdiaz8
 */
public abstract class EvaluationAlgorithm {
    protected float food;
    protected float house;
    protected float behaviour;
    protected float health;
    protected float total;
    protected final InterfaceEvaluationModel model;

    public EvaluationAlgorithm(InterfaceEvaluationModel model) {
        this.model=model;
        food=0;
        house=0;
        behaviour=0;
        health=0;
    }
    
    public static int necesaryNumberOfCows(int total){
        int result=(int)(total*0.1);
        if(result<5){
            result=5;
        }
        return result;
    }

    public float getBehaviour() {
        return behaviour;
    }

    public float getFood() {
        return food;
    }

    public float getHealth() {
        return health;
    }

    public float getHouse() {
        return house;
    }

    public float getTotal() {
        return total;
    }
    
    private float nota(Category cat){
        float media=0;
        int conteo=0;
        LinkedList<Valoration> fuckyou=model.listOfCategory(cat);
        for (Valoration val : fuckyou) {
            media+=val.getWeighing()*val.getNota();
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
    
    public void calcular(){
        float media=0;
        for (Category category : Category.values()) {
            media+=nota(category)*model.getWeighing(new IdCategory(category));
        }
        total= media/4;
    }
    
    @Override
    public String toString(){
        calcular();
        StringBuilder text=new StringBuilder();
        text.append("Resultados por promedio\n");
        
        text.append("Total: ");
        text.append(this.total);
        text.append("\n");
        
        text.append("Categoría Alimentación: ");
        text.append(this.food);
        text.append("\n");
        
        text.append("Categoría Salud: ");
        text.append(this.health);
        text.append("\n");
        
        text.append("Categoría Hogar: ");
        text.append(this.house);
        text.append("\n");
        
        text.append("Categoría Comportamiento: ");
        text.append(this.behaviour);
        text.append("\n");
        return text.toString();
    }
}

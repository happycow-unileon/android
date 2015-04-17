package es.unileon.happycow.model.facade;

import es.unileon.happycow.handler.IdCow;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.handler.Category;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.composite.CategoryComposite;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dorian
 */
public class CowModel implements InterfaceEvaluationModel{
    private final InterfaceEvaluationModel evaluation;
    private final HashMap<String, LinkedList<IdHandler>> cows;
    
    

    /**
     * Constructor
     * @param evaluation the model where is stored the evaluation
     */
    public CowModel(InterfaceEvaluationModel evaluation){
        //initialize the list of cows
        cows=new HashMap<>();
        this.evaluation = evaluation;
        //adjust the evaluation model to the model cow
        convertModel();
    }
    
    public int getNumberCows(){
        return cows.size();
    }
    
    /**
     * Convert the evaluation model to the cow evaluation model
     */
    private void convertModel(){
        //number of cows
        int numberCows=0;
        //get the list of criterions from the evaluation
        List<Criterion> listCriterion=evaluation.getListCriterion();
        //for every criterion...
        for (int i = 0; i < listCriterion.size(); i++) {
            //get the current criterion
            Criterion criterion = listCriterion.get(i);
            //get the list of valorations of the current criterion
            List<Valoration> listOneCriterion=evaluation.listOfCriterion(criterion.getIdCriterion());
            //for every valoration...
            for (int j = 0; j < listOneCriterion.size(); j++) {
                //get the valoration
                Valoration valoration = listOneCriterion.get(j);
                //if there is not enough cows...
                if(numberCows<(j+1)){
                    //create a new cow with the valoration and add to the list
                    IdHandler oneCow=new IdCow(numberCows+1);
                    numberCows++;
                    LinkedList<IdHandler> valorations=new LinkedList<>();
                    valorations.add(valoration.getId());
                    cows.put(oneCow.toString(),valorations);
                }else{
                    IdHandler nextCow=new IdCow(j+1);
                    //else, get the next cow and add the valoration
                    cows.get(nextCow.toString()).add(valoration.getId());
                }
                
            }
        }
    }

    /**
     * Get the list of valorations of the cow
     * @param idCow the identifier of the cow
     * @return the list of valorations
     */
    public List<Valoration> getValorations(IdHandler idCow) {
        List<Valoration> list= null;
        //get the list of id from the cow
        LinkedList<IdHandler> valorations=cows.get(idCow);
        
        if(valorations==null){
            valorations=new LinkedList<>();
        }
        
        //for every valoration it has...
        for (Iterator<IdHandler> it = valorations.iterator(); it.hasNext() && list==null;) {
            IdHandler val=it.next();
            //search and get the valoration
            list.add(evaluation.getValoration(val));
        }
        return list;
    }

    /**
     * Add a new valoration to a given cow
     * @param idCow the identifier of the cow
     * @param categoria
     * @param criterio
     * @param valoration valoration
     * @return true if the valoration is correctly added
     */
    public boolean addValoration(IdHandler idCow, 
            IdHandler categoria, Criterion criterio, Valoration valoration) {
        boolean result=true;
        result=result & evaluation.add(categoria, criterio);
        result=result & evaluation.add(categoria, criterio.getId(), valoration);
        
        LinkedList list=cows.get(idCow);
        if(list==null){
            cows.put(idCow.toString(), new LinkedList<IdHandler>());
        }
        result=result & cows.get(idCow).add(valoration.getId());
        
        //if any error, try to remove the inserted valoration
        if(!result){
            evaluation.remove(valoration.getId());
        }
        return result;
    }

    /**
     * Remove a valoration from a cow
     * @param idCow the identifier of the cow
     * @param idValoration the identifier of the valoration
     * @return 
     */
    public boolean  removeValoration(IdHandler idCow, IdHandler idValoration) {
        boolean result=true;
        
        LinkedList<IdHandler> valorations= cows.get(idCow);
        int index=-1;
        for (int i = 0; i < valorations.size() && index==-1; i++) {
            IdHandler idHandler = valorations.get(i);
            if(idHandler.compareTo(idValoration)==0){
                index=i;
            }
        }
        //if founded, remove the valoration from the cow
        if(index!=-1){
            result=result & valorations.remove(index)!=null;
        }
        
        //if found the valoration in the cow, remove from the evaluation
        if(result){
            result=result & evaluation.remove(idValoration);
        }
        
        //no luck? restore the valoration to the cow
        if(!result){
            valorations.add(idValoration);
        }
        
        //return result
        return result;
    }

    @Override
    public boolean add(IdHandler categoria, IdHandler criterio, Valoration valoration) {
        return evaluation.add(categoria, criterio, valoration);
    }

    @Override
    public boolean add(IdHandler categoria, Criterion criterion) {
        return evaluation.add(categoria, criterion);
    }

    @Override
    public boolean add(IdHandler categoria) {
        return evaluation.add(categoria);
    }

    @Override
    public boolean add(CategoryComposite categoria) {
        return evaluation.add(categoria);
    }

    @Override
    public boolean remove(IdHandler idHandler) {
        return evaluation.remove(idHandler);
    }

    @Override
    public boolean setWeighing(IdHandler idHandler, float weighing) {
        return evaluation.setWeighing(idHandler, weighing);
    }

    @Override
    public float getWeighing(IdHandler idHandler) {
        return evaluation.getWeighing(idHandler);
    }

    @Override
    public LinkedList listOfCategory(IdHandler category) {
        return evaluation.listOfCategory(category);
    }

    @Override
    public LinkedList<Valoration> listOfCategory(Category category) {
        return evaluation.listOfCategory(category);
    }

    @Override
    public LinkedList<Valoration> listOfCriterion(IdHandler criterion) {
        return evaluation.listOfCriterion(criterion);
    }

    @Override
    public LinkedList<Criterion> getListCriterion(Category category) {
        return evaluation.getListCriterion(category);
    }

    @Override
    public LinkedList<Criterion> getListCriterion() {
        return evaluation.getListCriterion();
    }

    @Override
    public IdHandler getIdHandler() {
        return evaluation.getIdHandler();
    }

    @Override
    public Criterion getCriterion(IdHandler id) {
        return evaluation.getCriterion(id);
    }

    @Override
    public InformationEvaluation getInformation() {
        return evaluation.getInformation();
    }

    @Override
    public Valoration getValoration(IdHandler id) {
        return evaluation.getValoration(id);
    }
    
    
}

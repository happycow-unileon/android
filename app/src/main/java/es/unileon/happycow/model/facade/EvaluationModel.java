package es.unileon.happycow.model.facade;

import es.unileon.happycow.database.Database;

import es.unileon.happycow.model.composite.CategoryComposite;
import es.unileon.happycow.model.composite.Component;
import es.unileon.happycow.model.composite.Composite;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Evaluation;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.iterator.Iterator;
import es.unileon.happycow.model.iterator.IteratorException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluationModel implements InterfaceEvaluationModel {

    private Evaluation _evaluation;
    /**
     * Indica si la evaluación está actualizada con todos sus datos (valoraciones)
     */
    private boolean updated;
    
    
    
    /**
     * Crear otro constructor al que se le pase updated pero no info (en su lugar pasa un IdHandler que sería el idFarm) y creas tu la info
     * Otro al que sólo se le pasa el idFarm(IdHandler), por lo que creas tu la info y updated queda a true
     * @param update
     * @param idFarm
     */
    public EvaluationModel(boolean update, IdHandler idFarm) {
        this(update, new InformationEvaluation(
                new IdEvaluation(Database.getInstance(null).nextIdEvaluation()), idFarm,
                Database.getInstance(null).getNumberCow(idFarm)));
   }

    public EvaluationModel(IdHandler idFarm) {
        this(true, new InformationEvaluation(
                new IdEvaluation(Database.getInstance(null).nextIdEvaluation()),
        idFarm, Database.getInstance(null).getNumberCow(idFarm)));
    }
    
    /**
     * 
     * @param updated
     * @param info 
     */
    public EvaluationModel(boolean updated, InformationEvaluation info) {
        this.updated=updated;
        

        _evaluation = new Evaluation(info.getIdEvaluation(), info);
        _evaluation.setRoot(_evaluation);
        _evaluation.setParent(null);
        _evaluation.add(new CategoryComposite(new IdCategory(Category.BEHAVIOUR)));
        _evaluation.add(new CategoryComposite(new IdCategory(Category.FOOD)));
        _evaluation.add(new CategoryComposite(new IdCategory(Category.HEALTH)));
        _evaluation.add(new CategoryComposite(new IdCategory(Category.HOUSE)));
    }

    public EvaluationModel(InformationEvaluation info) {
        this(true, info);
    }

    public boolean isUpdated() {
        return updated;
    }

    public void show() {
        _evaluation.show(0);
    }

    @Override
    public boolean add(IdHandler categoria, IdHandler criterio, Valoration valoration) {
        Component auxCategoria = null;
        Component auxCriterio = null;

        auxCategoria = _evaluation.search(categoria);
        auxCriterio = _evaluation.search(criterio);

        if(auxCriterio==null){
            add(categoria, new Criterion(criterio, categoria));
            auxCriterio = _evaluation.search(criterio);
        }

        if (auxCategoria != null && auxCriterio != null) {
            if (!auxCriterio.isLeaf()) {
                Composite aux = (Composite) auxCriterio;
                return aux.add(valoration);
            }
        }

        return false;
    }

    @Override
    public boolean add(IdHandler categoria, Criterion criterion) {
        Component auxCategoria = null;

        auxCategoria = _evaluation.search(categoria);

        if (auxCategoria != null) {
            if (!auxCategoria.isLeaf()) {
                Composite aux = (Composite) auxCategoria;
                return aux.add(criterion);
            }
        }

        return false;
    }

    /**
     * Se podria sobrecargar y que tambien permitiera pasar una objeto CategoryComposite
     */
    @Override
    public boolean add(IdHandler categoria) {
        Component auxCategory = null;
        auxCategory = _evaluation.search(categoria);

        if (auxCategory != null) {
            return _evaluation.add(new CategoryComposite(categoria)); //Creo y anhado la categoria
        }

        return false;
    }

    @Override
    public boolean add(CategoryComposite categoria) {

        if (_evaluation.search(categoria.getId()) != null) {
            return _evaluation.add(categoria);
        }

        return false;
    }

    @Override
    public boolean setWeighing(IdHandler idHandler, float weighing) {
        Component aux = _evaluation.search(idHandler);

        if (aux != null) {
            if (aux.isLeaf()) {
                Valoration v = (Valoration) aux;
                v.setWeighing(weighing);
                return true;
            } else {
                Composite c = (Composite) aux;
                c.setWeighing(weighing);
                return true;
            }
        }

        return false;
    }
    
    /**
     * Metodo auxiliar, ya que se tiene que pasar la instancia exacta del
     * component a buscar.
     * @param component
     * @return 
     */
    private boolean delete(Component component) {
        return _evaluation.delete(component);
    }
    
    @Override
    public boolean remove(IdHandler idHandler) {
        Component aux = _evaluation.search(idHandler); //Buscamos de forma recursiva la instancia
        
        if(aux != null){ //Si existe
            return delete(aux); //la borramos
        }
        
        return false; //si no existe devolvemos false
    }

    @Override
    public float getWeighing(IdHandler idHandler) {
        Component component = _evaluation.search(idHandler);
        if (component != null) {
            return component.getWeighing();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public LinkedList<Valoration> listOfCategory(IdHandler category) {
        LinkedList<Valoration> list = new LinkedList<Valoration>();

        Component c = _evaluation.search(category);

        if (c != null) {
            Composite categoria = (Composite) c;
            try {
                Iterator<Component> it = categoria.iterator(new String[]{});
                while (it.hasNext()) {
                    Component c1 = it.next();
                    if (c1.isLeaf()) {
                        list.add((Valoration) c1);
                    }
                }
            } catch (IteratorException ex) {

            }

        } else {

        }

        return list;
    }

    @Override
    public LinkedList<Valoration> listOfCategory(Category category) {
        return listOfCategory(new IdCategory(category));
    }

    @Override
    public LinkedList<Valoration> listOfCriterion(IdHandler criterion) {
        LinkedList<Valoration> list = new LinkedList<Valoration>();
        try {
            Component aux = null;
            
            Iterator<Component> it = _evaluation.iterator();
            while(it.hasNext()){
                Component component = it.next();
                if(component.getId().compareTo(criterion) == 0){
                    aux = component; 
                    break;
                }
            }
            
            if(aux != null){
                Iterator<Component> iterator = aux.iterator();
                while(iterator.hasNext()){
                    Component c = iterator.next();
                    if(c.isLeaf())
                        list.add((Valoration) c);
                }
            }else{
                
            }
        } catch (IteratorException ex) {
            Logger.getLogger(EvaluationModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    @Override
    public LinkedList<Criterion> getListCriterion(Category category) {
        LinkedList<Criterion> list = new LinkedList<Criterion>();
        
        for(Component categoria : _evaluation.getChildren()) {
            if(categoria.getId().compareTo(new IdCategory(category)) == 0){
                if(!categoria.isLeaf()){
                Composite auxCategoria = (Composite)categoria;
                for(Component criterio : auxCategoria.getChildren()){
                    list.add((Criterion) criterio); // Aqui hago un cast, si es feo, mejor dicho horrible... pero yo lo valgo xP jajaj
                }
            }
            }

        }
        
        return list;
    }

    @Override
    public LinkedList<Criterion> getListCriterion() {
        LinkedList<Criterion> list = new LinkedList<Criterion>();
        
        for(Component categoria : _evaluation.getChildren()) {
            if(!categoria.isLeaf()){
                Composite auxCategoria = (Composite)categoria;
                for(Component criterio : auxCategoria.getChildren()){
                    list.add((Criterion) criterio); // Aqui hago un cast, si es feo, mejor dicho horrible... pero yo lo valgo xP jajaj
                }
            }
        }
        
        return list;
    }

    @Override
    public IdHandler getIdHandler() {
        return this._evaluation.getId();
    }

    @Override
    public InformationEvaluation getInformation() {
        return _evaluation.getInformation();
    }

    @Override
    public Criterion getCriterion(IdHandler id) {
        Component c=_evaluation.search(id);
        if(c!=null){
            return (Criterion)c;
        }else{
            return null;
        }
    }

    @Override
    public Valoration getValoration(IdHandler id) {
        Component target=_evaluation.search(id);
        if(target!=null){
            return (Valoration)target;
        }else{
            return null;
        }
    }
}

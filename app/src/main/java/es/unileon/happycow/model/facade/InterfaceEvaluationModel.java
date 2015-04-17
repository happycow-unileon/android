package es.unileon.happycow.model.facade;

import es.unileon.happycow.model.composite.CategoryComposite;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.handler.*;
import es.unileon.happycow.model.InformationEvaluation;
import es.unileon.happycow.model.composite.Component;
import java.io.Serializable;
import java.util.LinkedList;

public interface InterfaceEvaluationModel extends Serializable{

    /**
     * Todas las operaciones que se quiernan realizar sobre el modelo deben
     * anhadirse aqui
     *
     */
    /**
     * Anhade una valoracion a una categoria y criterio dado
     *
     * @param categoria
     * @param criterio
     * @param valoration
     * @return
     */
    public boolean add(IdHandler categoria, IdHandler criterio, Valoration valoration);

    /**
     * Anhade un criterio a una categoria dada
     *
     * @param categoria
     * @param criterion
     * @return
     */
    public boolean add(IdHandler categoria, Criterion criterion);

    /**
     * Anhade una categoria, si no existe.
     *
     * @param categoria
     * @return
     */
    public boolean add(IdHandler categoria);

    /**
     * Anhade una categoria si no existe
     *
     * @param categoria
     * @return
     */
    public boolean add(CategoryComposite categoria);
    
    /**
     * Borra del arbol dado un handler
     * @param idHandler
     * @return 
     */
    public boolean remove(IdHandler idHandler);

    /**
     * Anhade una ponderacion dado un handler
     *
     * @param idHandler
     * @param weighing
     * @return
     */
    public boolean setWeighing(IdHandler idHandler, float weighing);
    
    /**
     * Devuelve la ponderacion dado el handler
     * @param idHandler
     * @return 
     */
    public float getWeighing(IdHandler idHandler);

    /**
     * Get a list of valorations filtered by a category
     *
     * @param category a category like a filter
     * @return a list of valorations
     */
    public LinkedList listOfCategory(IdHandler category);
    
    /**
     * 
     * @param category
     * @return 
     */
    public LinkedList<Valoration> listOfCategory(Category category);

    /**
     * Get a list of valorations filtered by a criterion
     *
     * @param criterion the criterion to filter
     * @return a list of valorations
     */
    public LinkedList<Valoration> listOfCriterion(IdHandler criterion);

    /**
     * Devueleve una lista de los criterios concretos 
     * que hay en una categoria concreta de la evaluacion
     *
     * @param category
     * @return una lista de criterios
     */
    public LinkedList<Criterion> getListCriterion(Category category);
    
    /**
     * Devuelve una lista de todos los criterios que hay en la evaluacion
     * @return una lista de criterios
     */
    public LinkedList<Criterion> getListCriterion();
    
    /**
     * Devuelve el handler de la evaluacion
     * @return handler de la evaluacion
     */
    public IdHandler getIdHandler();
    
    public Criterion getCriterion(IdHandler id);
    
    public InformationEvaluation getInformation();
    
    public Valoration getValoration(IdHandler id);
}

package es.unileon.happycow.database;

import es.unileon.happycow.handler.Category;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.composite.Criterion;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.composite.Valoration;
import es.unileon.happycow.model.facade.EvaluationModel;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author dorian
 */
public interface DataBaseOperations {
    //public DataBaseOperations getInstance();
    
    /**
     * Limpia la base de datos para hacer el backup
     * @return 
     */
    public boolean clearDB();
    
    public void startTransaccion() throws SQLException;

    public void rollback();

    public void commit();

    public void executeRawQuery(LinkedList<String> list);

    
    public boolean saveEvaluationBackup(InterfaceEvaluationModel evaluation);

    /**
     * Operaciones que se necesite para abrir/conectar la base de datos
     * @return 
     */
    public boolean openDB();

    public boolean isOpen();

    /**
     * Operaciones que se necesite para cerrar/desconectar de la base de 
     * datos de forma segura
     * @return 
     */
    public boolean closeDB();

    /**
     * Comprueba si los datos son correctos
     * @param user
     * @param passwd
     * @return un string con el error si el usuario y la contraseña son incorrectos, en caso contrario, string vacio
     */
    public String login(String user, String passwd);

    /**
     * Devuelve el usuario que está logeado
     * @return 
     */
    public User getUser();

    /**
     * Desconecta el usuario
     */
    public void logout();

    /**
     * Devuelve una lista de granjas del usuario con su información y listado de evaluaciones
     * pero sin sus valoraciones
     * @return 
     */
    public LinkedList<Farm> getListFarms();

    /**
     * Devuelve una lista de granjas del usuario deshabilitadas
     * @return 
     */
    public LinkedList<Farm> getDisabledFarms();

    /**
     * Devuelve el usuario especificado en el id
     * @param id
     * @return 
     */
    public User getUser(IdHandler id);

    /**
     * Devuelve una lista de evaluaciones de la granja especificada. No devuelve
     * las valoraciones de las evaluaciones
     * @param idFarm el identificador de la granja
     * @return lista de evaluaciones (interface, instanciados con evaluationModel)
     */
    public LinkedList<InterfaceEvaluationModel> getListEvaluations(IdHandler idFarm);

    /**
     * Devuelve una evaluacion con todas sus valoraciones (al contrario que las demás,
     * en new EvaluationModel, se pasa un true en vez de false)
     * @param id identificador de la evaluación
     * @return 
     */
    public InterfaceEvaluationModel getEvaluation(IdHandler id);

    /**
     * Devuelve la granja especificada
     * @param id identificador de la granja
     * @return 
     */
    public Farm getFarm(IdHandler id);
    
    /**
     * Devuelve el número de vacas que tiene la granja en una determinada evaluacion
     * @param id
     * @return 
     */
    public int getNumberCow(IdHandler id, IdHandler evaluation);
    
    public int getNumberCow(IdHandler id);

    /**
     * Añade un nuevo usuario
     * @param user usuario nuevo
     * @return true si todo correcto
     */
    public boolean newUser(User user);
    
    
    public boolean changePassword(IdHandler user, String password);
    
    /**
     * Elimina un usuario concreto
     * @param user el usuario a eliminar
     * @return true si todo correcto
     */
    public boolean removeUser(User user);

    /**
     * Añade un nuevo criterio
     * @param criterion el criterio a añadir
     * @return true si todo correcto 
     */
    public boolean newCriterion(Criterion criterion);
    
    /**
     * Devuelve el criterio especificado
     * @param id identificador del criterio
     * @return 
     */
    public Criterion getCriterion(IdHandler id);

    public LinkedList<Criterion> getListCriterion(Category category);

    /**
     * METODO PRIVADO!! este método no ha de estar en la interfaz, pero ayuda
     * tenerlo
     * @param evaluation
     * @return 
     */
    //public boolean saveEvaluation(InformationEvaluation evaluation);

    /**
     * METODO PRIVADO!! este método no ha de estar en la interfaz, aunque la tengas
     * en la clase database
     * @param evaluation
     * @return 
     */
    //public boolean saveModifiedEvaluation(InformationEvaluation evaluation); //borrar y volver a introducir
    
    /**
     * Guarda los datos de una evaluación 
     * @param evaluation evaluación a guardar
     * datos
     * @return 
     */
    public boolean saveEvaluation(InterfaceEvaluationModel evaluation);

    /**
     * Guarda los datos de una evaluación ya existente
     * @param evaluation evaluación a guardar que ya se encuentra en la base de
     * datos
     * @return 
     */
    public boolean saveModifiedEvaluation(InterfaceEvaluationModel evaluation);

    /**
     * Guarda una nueva granja
     * @param farm la granja a guardar
     * @return 
     */
    public boolean newFarm(Farm farm);
    
    /**
     * ELimina una granja de la base de datos
     * @param farm
     * @return 
     */
    public boolean removeFarm(Farm farm);

    /**
     * Guarda una granja ya existente en la base de datos
     * @param farm la granja a guardar que ya existe
     * @return 
     */
    public boolean modifiedFarm(Farm farm);

    /**
     * Deshabilita la granja especificada
     * @param id identificador de la granja a deshabilitar
     * @return 
     */
    public boolean disableFarm(IdHandler id);

    /**
     * Habilita la granja especificada
     * @param id identificador de la granja
     * @return 
     */
    public boolean enableFarm(IdHandler id);

    /**
     * Devuelve todos los criterios existentes en la base de datos
     * @return 
     */
    public LinkedList<Criterion> getListCriterion();

    /**
     * Elimina un criterio específico
     * @param idCriterion el identificador del criterio a eliminar
     * @return 
     */
    public boolean removeCriterion(IdHandler idCriterion);

    /**
     * Devuelve el siguiente identificador de una evaluacion.
     * cuando creamos una nueva evaluación, le tenemos que pasar un número 
     * como identificador, que se usa en la base de datos por ello, se hace 
     * una consulta para saber cuántas evaluaciones tiene ya guardadas para saber cuál es el siguiente número que podemos poner al nuevo
     * @return 
     */
    public int nextIdEvaluation();

    /**
     * Devuelve el siguiente identificador de una granja.
     * lo mismo que antes le pasa a la granja ya que ambos tienen índices que van por conteo simple
     * @return 
     */
    public int nextIdFarm();

    /**
     * Devuelve el identificador de una valoración.
     * ATENCIÓN: aquí se ha de tener en cuenta que se pedirá varias veces sin
     * ir guardando en la base de datos, por lo que se debe mantener una variable en
     * la clase para ir incrementándolo después de la primera consulta
     * @return 
     */
    public int nextIdValoration();

    /**
     * Devuelve una granja especificada de un determinado usuario
     * @param idUser el usuario al que pertenece la granja
     * @param idFarm el identificador de la granja
     * @return 
     */
    public Farm getFarm(IdHandler idUser, IdHandler idFarm);

    /**
     * Devuelve una lista de granjas de un determinado usuario
     * @param idUser
     * @return 
     */
    public LinkedList<Farm> getListFarms(IdHandler idUser);
    
    /**
     * Devuelve una lista de usuarios existentes en la base de datos
     * @return 
     */
    public LinkedList<User> getListUsers();

    /**
     * Devuelve una lista de evaluaciones de un determinado usuario y una determinada
     * granja. NO saca las valoraciones
     * @param idUser
     * @param idFarm
     * @return 
     */
    public LinkedList<InterfaceEvaluationModel> getListEvaluations(IdHandler idUser, IdHandler idFarm);
    
    /**
     * Devuelve todo el contenido de la tabla granjas, devolviendo una lista de
     * granjas (sin más datos que los de la tabla granjas)
     * @return 
     */
    public LinkedList<Farm> getAllFarms();
    
    /**
     * Devuelve una lista de todos los datos de la tabla evaluaciones, devolviendo
     * una lista de evaluaciones, pero sin más datos que la tabla evaluaciones
     * @return 
     */
    public LinkedList<EvaluationModel> getAllEvaluations();
    
    /**
     * Devuelve una lista de todos los datos de la tabla valoraciones, sin más
     * datos que la tabla valoraciones
     * @return 
     */
    public LinkedList<ValorationDB> getAllValorations();
    
    
    /**
     * Guardar la valoración con todos los datos dados.
     * @param idEvaluation
     * @param criterion
     * @param valoration 
     * @return  
     */
    public boolean saveValoration(int idEvaluation, Criterion criterion, Valoration valoration);
    
    public boolean saveValoration(ValorationDB valoration);
    
    /**
     * Devuelve toda la tabla de ponderaciones de categorías, mapeadas en la clase
     * de PonderationDB
     * @return 
     */
    public LinkedList<PonderationDB> getCriterionPonderation();
    
    /**
     * Devuelve toda la tabla de ponderaciones de criterios, mapeadas en la clase
     * de PonderationDB
     * @return 
     */
    public LinkedList<PonderationDB> getCategoryPonderation();
    
    /**
     * Guarda en la base de datos todas las ponderaciones de categorías
     * @param list lista de ponderaciones a guardar
     * @return 
     */
    public boolean saveCategoryPonderation(LinkedList<PonderationDB> list);
    
    /**
     * Guarda en la base de datos todas las ponderaciones de criterios
     * @param list lista de ponderaciones a guardar
     * @return 
     */
    public boolean saveCriterionPonderation(LinkedList<PonderationDB> list);
    
    public boolean removeEvaluation(IdHandler id);
    
}

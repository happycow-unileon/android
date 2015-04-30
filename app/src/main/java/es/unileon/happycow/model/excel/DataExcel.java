package es.unileon.happycow.model.excel;

import es.unileon.happycow.database.Database;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.model.Farm;
import es.unileon.happycow.model.User;
import es.unileon.happycow.model.facade.InterfaceEvaluationModel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
//import org.japura.gui.model.DefaultListCheckModel;

/**
 *
 * @author dorian
 */
public class DataExcel {
    private final LinkedHashMap<User, LinkedList<Farm>> model;
    private final HashMap<String, ListCheckModel> evaluations;
    private final HashMap<String, ListCheckModel> farms;
    private final HashMap<String, IdHandler> mapBetweenIdDate;
    private final HashMap<String, IdHandler> mapBetweenIdName;

    public DataExcel(LinkedHashMap<User, LinkedList<Farm>> model) {
        this.model = model;
        mapBetweenIdName=new HashMap<>();
        mapBetweenIdDate=new HashMap<>();
        evaluations=new HashMap<>();
        farms=new HashMap<>();

        for (User user : model.keySet()) {
            ListCheckModel listFarm=new ListCheckModel();

            for (Farm farm : model.get(user)) {
                ListCheckModel listEvaluation=new ListCheckModel();
                mapBetweenIdName.put(farm.getFarmName(), farm.getIdFarm());
                listFarm.addElement(farm.getFarmName());

                for (InterfaceEvaluationModel interfaceEvaluationModel :
                        Database.getInstance(null).getListEvaluations(user.getId(), farm.getIdFarm())){//farm.getListEvaluation()) {
                    String date=interfaceEvaluationModel.getInformation().getFecha().toString();
                    mapBetweenIdDate.put(date, interfaceEvaluationModel.getIdHandler());
                    listEvaluation.addElement(date);
                }
                listEvaluation.checkAll();
                evaluations.put(farm.getIdFarm().toString(), listEvaluation);
            }
            listFarm.checkAll();
            farms.put(user.getId().toString(), listFarm);
        }
    }

    public LinkedList<User> getUsers(){
        return new LinkedList<>(model.keySet());
    }

    public LinkedHashMap getModel(){
        return model;
    }

    public IdHandler getFarm(String farm){
        return mapBetweenIdName.get(farm);
    }

    public IdHandler getEvaluation(String evaluation){
        return mapBetweenIdDate.get(evaluation);
    }

    public ListCheckModel getEvaluations(IdHandler farm){
        return evaluations.get(farm.toString());
    }

    public ListCheckModel getFarms(IdHandler user){
        return farms.get(user.toString());
    }

}

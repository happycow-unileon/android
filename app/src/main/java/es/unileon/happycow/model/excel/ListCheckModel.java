package es.unileon.happycow.model.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dorian on 1/05/15.
 */
public class ListCheckModel {
    private HashMap<String, Boolean> list;

    public ListCheckModel(){
        list=new HashMap<>();
    }

    public void addElement(String name){
        list.put(name, false);
    }

    public void checkAll(){
        for (Map.Entry<String, Boolean> entry : list.entrySet()){
            entry.setValue(true);
        }
    }
}

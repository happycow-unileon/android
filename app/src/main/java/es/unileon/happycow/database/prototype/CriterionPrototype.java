package es.unileon.happycow.database.prototype;

import es.unileon.happycow.model.composite.Criterion;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author dorian
 */
public class CriterionPrototype {

    private HashMap<String, Criterion> list;

    public CriterionPrototype() {
        list = new HashMap<>();
    }

   
    public void add(Criterion... criterions) {
        for (Criterion criterion : criterions) {
            list.put(criterion.getName(), criterion);
        }
    }

    public void remove(Criterion... criterions) {
            for (Criterion criterion : criterions) {
                list.remove(criterion.getName());
            }
    }

    public LinkedList<Criterion> getList() {
        return new LinkedList<>(list.values());
    }

    public Criterion clone(String name) {
        Criterion criterion = null;
        try {
            criterion = (Criterion) list.get(name).clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return criterion;
    }

}

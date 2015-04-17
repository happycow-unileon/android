package es.unileon.happycow.handler;

/**
 *
 * @author dorian
 */

public class IdCriterion implements IdHandler{
    private String name;

    public IdCriterion(String name) {
        this.name = name;
    }

    public IdCriterion(IdHandler id) {
        try {
            IdCriterion idCriterion=(IdCriterion)id;
            this.name=idCriterion.getName();
        } catch (Exception e) {
            throw new IllegalArgumentException("No es un identificador de criterion");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(IdHandler another) {
        return this.name.compareTo(another.toString());
    }
}

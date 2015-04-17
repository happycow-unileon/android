package es.unileon.happycow.handler;

import java.io.Serializable;

/**
 *
 * @author dorian
 */
public class IdCow implements IdHandler, Serializable{
    private int id;

    public IdCow(int id) {
        this.id = id;
    }
    
    public IdCow(IdHandler id){
        try {
            IdCow cow = (IdCow) id;
            this.id = cow.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("No es un identificador de valoración");
        }
    }
    
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int compareTo(IdHandler another) {
        return String.valueOf(this.id).compareTo(another.toString());
    }
    
}

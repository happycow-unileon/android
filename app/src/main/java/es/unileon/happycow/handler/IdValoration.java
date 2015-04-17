package es.unileon.happycow.handler;

/**
 *
 * @author dorian
 */
public class IdValoration implements IdHandler{

    private final int id;

    public IdValoration(int id) {
        this.id = id;
    }

    public IdValoration(IdHandler id) {
        try {
            IdValoration valoration = (IdValoration) id;
            this.id = valoration.getId();
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
        return this.toString().compareTo(another.toString());
    }
}

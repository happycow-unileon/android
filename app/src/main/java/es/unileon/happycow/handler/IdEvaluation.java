package es.unileon.happycow.handler;


/**
 *
 * @author dorian
 */
public class IdEvaluation implements IdHandler{

    private final int identifier;

    public IdEvaluation(int identifier) {
        this.identifier = identifier;
    }
    
    public IdEvaluation(IdHandler id){
        try {
            IdEvaluation evaluation = (IdEvaluation) id;
            this.identifier=evaluation.getIdentifier();
        } catch (Exception e) {
            throw new IllegalArgumentException("No es un identificador de valoración");
        }
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return String.valueOf(identifier);
    }

    @Override
    public int compareTo(IdHandler another) {
        return this.toString().compareTo(another.toString());
    }
}

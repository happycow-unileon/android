package es.unileon.happycow.handler;


/**
 *
 * @author dorian
 */
public class IdUser implements IdHandler{
    private final String user;

    public IdUser(String user) {
        this.user = user;
    }
    
    public IdUser(IdHandler id){
        try {
            IdUser usuario=(IdUser)id;
            this.user=usuario.getUser();
        } catch (Exception e) {
            throw new IllegalArgumentException("No es un identificador de usuario");
        }
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user;
    }

    @Override
    public int compareTo(IdHandler another) {
        return this.toString().compareTo(another.toString());
    }
}

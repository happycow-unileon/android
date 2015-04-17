package es.unileon.happycow.handler;

import java.io.Serializable;

/**
 *
 * @author dorian
 */
public class IdFarm implements IdHandler {

    private IdHandler idUser;
    private int idFarm;

    public IdFarm(int idFarm) {
        // this.idUser=Database.getInstance().getUser().getId();
        this.idFarm = idFarm;
    }

    public IdFarm(IdHandler idUser, int idFarm) {
        this.idUser = idUser;
        this.idFarm = idFarm;
    }

    public IdFarm(IdHandler idFarm) {
        try {
            IdFarm id = (IdFarm) idFarm;
            this.idUser = id.getIdUser();
            this.idFarm = id.getIdFarm();
        } catch (Exception e) {
            throw new IllegalArgumentException("El identificador no es de tipo granja");
        }
    }

    public int getIdFarm() {
        return idFarm;
    }

    public IdHandler getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return String.valueOf(idFarm);

    }

    @Override
    public int compareTo(IdHandler another) {
        IdFarm other = null;
        int result = 0;
        try {
            other = (IdFarm) another;
        } catch (Exception e) {
            result = -1;
        }
        if (result != -1) {
            if (this.idUser == null || other.getIdUser() == null || this.idUser.compareTo(other.getIdUser()) == 0) {
                result = this.toString().compareTo(another.toString());
            } else {
                result = -1;
            }
        }
        return result;
    }
}

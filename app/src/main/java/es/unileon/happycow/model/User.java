package es.unileon.happycow.model;

import es.unileon.happycow.database.concreteDatabase.AndroidSQLite;
import es.unileon.happycow.handler.IdHandler;
import es.unileon.happycow.handler.IdUser;
import java.io.Serializable;
//import es.unileon.happycow.procedures.PreferredView;

/**
 *
 * @author amdiaz8
 */
public class User implements Serializable{
    private String name;
    private String password;
    private IdHandler idHandler;
    private Rol rol;

    public User(String name, String passwd, Rol rol) {
        this(new IdUser(name), passwd, rol, false);
    }
    
    public User(String name, String passwd, Rol rol, boolean encripted) {
        this(new IdUser(name), passwd, rol, encripted);
    }
    
    public User(String name, String passwd, String rol){
        this(name, passwd, Rol.valueOf(rol), false);
    }
    
    public User(String name, String passwd, String rol, boolean encripted){
        this(name, passwd, Rol.valueOf(rol), encripted);
    }
    
    public User(IdHandler id, String passwd, String rol){
        this(id, passwd, Rol.valueOf(rol), false);
    }
    
    public User(IdHandler id, String passwd, String rol, boolean encripted){
        this(id, passwd, Rol.valueOf(rol), encripted);
    }
    
    
    public User(IdHandler id, String passwd, Rol rol){
        this(id, passwd, rol, false);
    }
    
    public User(IdHandler id, String passwd, Rol rol, boolean encripted){
        this.name=id.toString();
        this.idHandler=id;
        if(!encripted){
            this.password= AndroidSQLite.encript(passwd);
        }else{
            this.password=passwd;
        }
        this.rol=rol;
    }
   
    public String getName(){
        return this.name;
    }
    
    public String getPassword(){
        return this.password;
    }

    public Rol getRol() {
        return rol;
    }
    
    public String getStringRol(){
        return rol.toString();
    }
    
//    public PreferredView getPreferredView(){
//        return this.preferredView;
//    }
    
    public IdHandler getId(){
        return this.idHandler;
    }

    @Override
    public String toString() {
        return name + " " + password + " " + rol.toString();
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.happycow.webService;

/**
 *
 * @author dorian
 */
public class Register extends Command{
    private static final String COMMAND="register";

    public Register(String user, String pass, String rol, String email) {
        super();
        this.parameters.add(new Parameter("name", user));
        this.parameters.add(new Parameter("password", pass));
        this.parameters.add(new Parameter("rol", rol));
        this.parameters.add(new Parameter("email", email));
        
        this.path=COMMAND;
    }
    
}

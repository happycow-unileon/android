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
public class Login extends Command {
    private static final String COMMAND="login";

    public Login(String user, String pass) {
        super();
        this.parameters.add(new Parameter("name", user));
        this.parameters.add(new Parameter("password", pass));
        
        this.path=COMMAND;
    }
    
}

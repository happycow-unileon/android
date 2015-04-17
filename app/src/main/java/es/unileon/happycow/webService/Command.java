/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.happycow.webService;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dorian
 */
public abstract class Command {
    protected List<Parameter> parameters;
    protected String path;

    public Command() {
        parameters=new LinkedList<>();
        path="";
    }
    
    public String execute(){
        return HttpsRequest.request(path, parameters);
        //hago el Iserver.execute()
    };
}

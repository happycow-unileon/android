/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unileon.happycow.database;

import java.io.Serializable;

/**
 *
 * @author dorian
 */
public class PonderationDB implements Serializable{
    /**
     * Identificador de la evaluación
     */
    private int id;
    /**
     * Nombre de lo que pondera (categoría o criterio)
     */
    private String name;
    /**
     * La ponderación
     */
    private float ponderation;

    public PonderationDB(int id, String name, float ponderation) {
        this.id = id;
        this.name = name;
        this.ponderation = ponderation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public float getPonderation() {
        return ponderation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPonderation(float ponderation) {
        this.ponderation = ponderation;
    }
    
}

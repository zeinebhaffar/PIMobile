/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author ANIS_LENOVO
 */
public class medecin {
    private String idmed;
    private String nommed;
    private String spécialité;
    private String région;

    public medecin() {
    }

    public medecin(String idmed, String nommed, String spécialité, String région) {
        this.idmed = idmed;
        this.nommed = nommed;
        this.spécialité = spécialité;
        this.région = région;
    }

    public medecin(String nommed, String spécialité, String région) {
        this.nommed = nommed;
        this.spécialité = spécialité;
        this.région = région;
    }

    public String getIdmed() {
        return idmed;
    }

    public void setIdmed(String idmed) {
        this.idmed = idmed;
    }

    public String getNommed() {
        return nommed;
    }

    public void setNommed(String nommed) {
        this.nommed = nommed;
    }

    public String getSpécialité() {
        return spécialité;
    }

    public void setSpécialité(String spécialité) {
        this.spécialité = spécialité;
    }

    public String getRégion() {
        return région;
    }

    public void setRégion(String région) {
        this.région = région;
    }

  
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final medecin other = (medecin) obj;
        if (this.idmed != other.idmed) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "medecin{" + "idmed=" + idmed + ", nommed=" + nommed + ", sp\u00e9cialit\u00e9=" + spécialité + ", r\u00e9gion=" + région + '}';
    }

    
}

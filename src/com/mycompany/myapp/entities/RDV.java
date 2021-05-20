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
public class RDV {
    private int id;
    private String nom;
    private String nommed;
    private String date;
    
    public RDV(){
}
    public RDV(int id, String nom, String nommed, String date){
       this.id=id;
       this.nom=nom;
       this.nommed=nommed;
       this.date=date;
    }
     public RDV( String nom, String nommed, String date){
       this.nom=nom;
       this.nommed=nommed;
       this.date=date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id= id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNommed() {
        return nommed;
    }

    public void setNommed(String nommed) {
        this.nommed = nommed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        final RDV other = (RDV) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RDV{" + "id=" + id + ", nom=" + nom + ", nommed=" + nommed + ", date=" + date + '}';
    }
    
}

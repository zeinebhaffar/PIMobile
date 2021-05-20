/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.esprit.utils.Statics;
import com.mycompany.myapp.entities.RDV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANIS_LENOVO
 */
public class ServiceRDV {
    
     public ArrayList<RDV> rdvs;
    
    public static ServiceRDV instance=null;
    public static boolean resultOK;
    private ConnectionRequest req;

    private ServiceRDV() {
         req = new ConnectionRequest();
    }

    public static ServiceRDV getInstance() {
        if (instance == null) {
            instance = new ServiceRDV();
        }
        return instance;
    }

    public boolean addRDV(RDV r) {
        String url = Statics.BASE_URL + "/rdv/new/" + r.getNom() + "/" + r.getNommed()+ "/" + r.getDate(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<RDV> parseRDV(String jsonText){
        try {
            rdvs=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                RDV r = new RDV();
                float id = Float.parseFloat(obj.get("id").toString());
                r.setId((int)id);
                r.setNom(obj.get("nom").toString());
                r.setNommed(obj.get("nommed").toString());
                r.setDate(obj.get("date").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                rdvs.add(r);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return rdvs;
    }
    
    public ArrayList<RDV> getAllRDV(){
        String url = Statics.BASE_URL+"/rdv/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rdvs = parseRDV(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return rdvs;
    }
    public boolean deleteRDV (int id){

        String url=Statics.BASE_URL+"/rdv/delete/"+id;
        req.setUrl(url);
        req.setPost(false);
      req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         req.removeResponseCodeListener(this);
            }
        });
      NetworkManager.getInstance().addToQueueAndWait(req);
      return resultOK;
    }
   
    public boolean EditRDV(RDV r){

        String url=Statics.BASE_URL+"/rdv/"+r.getId()+"/edit/"+r.getNom()+"/"+r.getNommed()+"/"+r.getDate();
        req.setUrl(url);
        req.setPost(false);
      req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         resultOK=req.getResponseCode()==200;
         req.removeResponseCodeListener(this);
            }
        });
      NetworkManager.getInstance().addToQueueAndWait(req);
      return resultOK;
    }
}

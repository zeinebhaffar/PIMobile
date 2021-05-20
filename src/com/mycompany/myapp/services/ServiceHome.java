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
import com.mycompany.myapp.entities.Home;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zeineb Haffar
 */
public class ServiceHome {
    public ArrayList<Home> homes;
    
    public static ServiceHome instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceHome() {
         req = new ConnectionRequest();
    }

    public static ServiceHome getInstance() {
        if (instance == null) {
            instance = new ServiceHome();
        }
        return instance;
    }

    public boolean addHome(Home r) {
        String url = Statics.BASE_URL + "/home/new/" + r.getTitle() + "/" + r.getImg()+ "/" + r.getImg2(); //création de l'URL
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

    public ArrayList<Home> parseHome(String jsonText){
        try {
            homes=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
              
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Home r = new Home();
                float id = Float.parseFloat(obj.get("id").toString());
                r.setId((int)id);
                r.setTitle(obj.get("title").toString());
                r.setImg(obj.get("img").toString());
                r.setImg2(obj.get("img2").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                homes.add(r);
            }
            
            
        } catch (IOException ex) {
            
        }
         /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        return homes;
    }
    
    public ArrayList<Home> getAllHome(){
        String url = Statics.BASE_URL+"/home/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                homes = parseHome(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return homes;
    }
   
    
}

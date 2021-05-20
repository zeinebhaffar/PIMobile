/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;


/**
 *
 * @author ANIS_LENOVO
 */
public class HomeForm extends BaseForm{
    Form current;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
    aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
    la méthode showBack*/
    
    public HomeForm(Resources res) {
         super("Newsfeed",BoxLayout.y());
    //    Toolbar tb=new Toolbar(true);
        current =this;
    //    setToolbar(tb);
       super.addSideMenu(res);
        getTitleArea().setUIID("Container");
      //  setTitle("Ajouter Publication");
        getContentPane().setScrollVisible(false);
       
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        Button btnAddRDV = new Button("Add RDV");
        Button btnListRDV = new Button("List RDV");
        Button btnAddmedecin = new Button("Add medecin");
        Button btnListmedecin = new Button("List medecin");

        btnAddRDV.addActionListener(e -> new AddRDVForm(res).show());
        btnListRDV.addActionListener(e -> new ListRDVForm(res).show());
        btnAddmedecin.addActionListener(e -> new AddmedecinForm(res).show());
        btnListmedecin.addActionListener(e -> new ListmedecinForm(current).show());
        addAll(btnAddRDV, btnListRDV,btnAddmedecin, btnListmedecin);

    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.RDV;
import com.mycompany.myapp.services.ServiceRDV;

/**
 *
 * @author Zeineb Haffar
 */
public class AddmedecinForm extends BaseForm {
    Form current;  
    public AddmedecinForm(Resources res) {
        current = this;
        this.addSideMenu(res);
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        setTitle("Ajouter un nouveau medecin");
        setLayout(BoxLayout.y());
        
        TextField tfnommed = new TextField("","Nom du med");
        TextField tfspécialité= new TextField("", "spécialité");
        TextField tfrégion= new TextField("", "région");

        Button btnValider = new Button("Ajouter RDV");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnommed.getText().length()==0)||(tfspécialité.getText().length()==0)||(tfrégion.getText().length()==0))
                    Dialog.show("Alerte", "Veuillez remplir tous les champs svp!", new Command("OK"));
                else
                {
                    try {
                        RDV r = new RDV(tfnommed.getText(),tfspécialité.getText(),tfrégion.getText());
                        if( ServiceRDV.getInstance().addRDV(r))
                            Dialog.show("Success","Medecin bien ajouté",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfnommed,tfspécialité,tfrégion,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e->{} ); // Revenir vers l'interface précédente     previous.showBack()
                
}
}
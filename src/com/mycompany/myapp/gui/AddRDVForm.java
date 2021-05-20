/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.RDV;
import com.mycompany.myapp.services.ServiceRDV;
import com.codename1.components.ToastBar;
import com.codename1.components.ToastBar.Status;
import static com.codename1.ui.Component.BOTTOM;

/**
 *
 * @author ANIS_LENOVO
 */
public class AddRDVForm extends BaseForm{
  Form current;  
    public AddRDVForm(Resources res) {
        current = this;
        this.addSideMenu(res);
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        setTitle("Ajouter un nouveau RDV");
        setLayout(BoxLayout.y());
        
        TextField tfnom = new TextField("","Nom et Prénom");
        TextField tfnommed= new TextField("", "nommed");
        TextField tfdate= new TextField("", "date");
        //Picker datePicker = new Picker();
        //datePicker.setType(Display.PICKER_TYPE_DATE);

        Button btnValider = new Button("Ajouter RDV");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfnom.getText().length()==0)||(tfnommed.getText().length()==0)||(tfdate.getText().length()==0))
                    Dialog.show("Alerte", "Veuillez remplir tous les champs svp!", new Command("OK"));
                else
                    
                {
                    try {
                        RDV r = new RDV(tfnom.getText(),tfnommed.getText(),tfdate.getText());
                        if( ServiceRDV.getInstance().addRDV(r))
                            Dialog.show("Success","RDV bien ajouté",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        ToastBar.getInstance().setPosition(BOTTOM);
           ToastBar.Status status = ToastBar.getInstance().createStatus();
           status.setShowProgressIndicator(true);
           status.setIcon(res.getImage("unnamed.jpg").scaledSmallerRatio(Display.getInstance().getDisplayWidth()/10, Display.getInstance().getDisplayWidth()/15));
           status.setMessage("Votre rendez-vous est ajouté avec succés");
           status.setExpires(10000);  // only show the status for 3 seconds, then have it automatically clear
           status.show();
               //  iDialog.dispose(); //NAHIW LOADING BAED AJOUT
           new ListRDVForm(res).show();

                 refreshTheme();
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", " ", new Command("OK"));
                        
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfnom,tfnommed,tfdate,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK , e->new DisplayHomeForm(res).show() );
        // Revenir vers l'interface précédente     previous.showBack()
             
         
        
    }
    
    
}

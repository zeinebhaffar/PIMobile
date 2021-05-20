/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.gif.GifImage;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.getResourceAsStream;
import static com.codename1.ui.CN.log;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;

import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;



import com.esprit.utils.Statics;
import com.mycompany.myapp.entities.RDV;
import com.mycompany.myapp.services.ServiceRDV;
import java.io.IOException;
import java.util.ArrayList;






/**
 *
 * @author Letaief Sofiene
 */
public class ListRDVForm extends BaseForm {

  Form current;
    public ListRDVForm(Resources res) {
       
      super("Liste des rendez-vous",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
  current=this;
    //    livraisonTheme = res;
        Toolbar tb = new Toolbar(true);

        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des rendez-vous");
        getContentPane().setScrollVisible(false);

       tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new DisplayHomeForm(res).show());
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("delivery.png"), spacer1, " ", " ", "All Deliveries ");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        radioContainer.setScrollableY(true);
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
       FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
     //   FloatingActionButton pub = fab.createSubFAB(FontImage.MATERIAL_ADD_TASK,"publication");
        fab.addActionListener(e->new AddRDVForm(res).show());
        // FloatingActionButton pubé = fab.createSubFAB(FontImage.MATERIAL_ADD_TASK,"publicatioDn");
                Dialog d = new Dialog("Trier par ");
        d.setLayout(new BorderLayout());
        Label prixbas = new Label("Prix le plus bas");
        Label prixeleve = new Label("Prix le plus éleves");
        Label favoris = new Label("les plus aimées");
        Label nomAsc = new Label("alphabet Z-A");
        Label nomDesc = new Label("alphabet A-Z ");
        favoris.setUIID("Label3");
        prixeleve.setUIID("Label3");
        nomAsc.setUIID("Label3");
        nomDesc.setUIID("Label3");
        prixbas.setUIID("Label3");

        favoris.setTextPosition(LEFT);
        prixeleve.setTextPosition(LEFT);
        nomAsc.setTextPosition(LEFT);
        nomDesc.setTextPosition(LEFT);
        prixbas.setTextPosition(LEFT);
        Style checkStyle = new Style(nomAsc.getUnselectedStyle());
        checkStyle.setFgColor(0xff2d55);
        FontImage checkImage = FontImage.createMaterial(FontImage.MATERIAL_CHECK, checkStyle);
        super.add(fab);
    ArrayList<RDV>list=ServiceRDV.getInstance().getAllRDV();
    for(RDV rec:list){
        String url3 = "http://127.0.0.1:8000/uploads/sa.jpg";   //  String url3="";
        Image placeHolder=Image.createImage(120,90);
        EncodedImage enc= EncodedImage.createFromImage(placeHolder,false);
        URLImage urli =URLImage.createToStorage(enc, url3, url3, URLImage.RESIZE_SCALE);
      
        
    addButton(urli,rec,res);
    ScaleImageLabel image =new ScaleImageLabel(urli);
Container cont = new Container();
image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);


    }
    }

    private void addButton(Image img ,RDV rec,Resources res) {
      int height =Display.getInstance().convertToPixels(20.5f);
       int width =Display.getInstance().convertToPixels(20f);
        Button image =new Button(img.fill(width,height));
       image.setUIID("Label");
       Container cnt=BorderLayout.west(image);
       Label nom=new Label(rec.getNom(),"NewsTopLine2");
        Label nommed=new Label(rec.getNommed(),"NewsTopLine2");
         Label date=new Label(rec.getDate(),"NewsTopLine2");
        
          
//        TextArea ta=new TextArea(nom_user);
//        ta.setUIID("NewsTopLine");
//        ta.setEditable(false);
//        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(ta));

        Label lSupp=new Label(" ");
        lSupp.setUIID("NewsTopLine2");
        Style supprimerStyle =new Style(lSupp.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage =FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        lSupp.setIcon(supprimerImage);
        lSupp.setTextPosition(RIGHT);
        
        lSupp.addPointerPressedListener(l->{
        Dialog dig =new Dialog("Suppression");
        if(dig.show(",suppression","Vous voulez Supprimer ce reclamation ?","Annuler","Oui")){
        dig.dispose();
        }else {
        
        dig.dispose();
         
        if(ServiceRDV.getInstance().deleteRDV(rec.getId())){
          new ListRDVForm(res).show();
        }
        
        
        
        }        
        });
   
        
            Label lModi=new Label(" ");
        lModi.setUIID("NewsTopLine");
        Style modifStyle =new Style(lModi.getUnselectedStyle());
        modifStyle.setFgColor(0xf7ad02);
        FontImage modifieImage =FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifStyle);
        lModi.setIcon(modifieImage);
        lModi.setTextPosition(LEFT);
        
        lModi.addPointerPressedListener(l->{
     new EditRDVForm(res,rec).show();
        
        });
//        
//     nomuser.addPointerPressedListener(l->{
//     new displayProduitForm(img,res,rec).show();
//        
//        });
//        prenomuser.addPointerPressedListener(l->{
//    new displayProduitForm(img,res,rec).show(); });
//        
//           image.addPointerPressedListener(l->{
//      new displayProduitForm(img,res,rec).show(); });
        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
                BoxLayout.encloseX(nom),
                BoxLayout.encloseX(nommed,lModi,lSupp), 
                BoxLayout.encloseX(date)));
        
        
        
        add(cnt);
        
  //      super.createLineSeparator();
    }
     private void addTab(Tabs swipe, Image image, Label spacer1, String likesStr, String commentsStr, String all_Deliveries_) {
         int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
//        if(img.getHeight() < size) {
//            img = img.scaledHeight(size);
//        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
//        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
//            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
//        }
//        ScaleImageLabel image = new ScaleImageLabel(img);
//        image.setUIID("Container");
//        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        ScaleImageLabel imgg = new ScaleImageLabel();
        try {
            imgg = new ScaleImageLabel(GifImage.decode(getResourceAsStream("/RDV.gif"), 5156565));
        } catch (IOException err) {
            log(err);
        }
        imgg.setUIID("Container");
        imgg.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Container page1
                = LayeredLayout.encloseIn(
                        imgg,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY( //                            new SpanLabel(text, "LargeWhiteText"),
                                //                            FlowLayout.encloseIn(likes, comments),
                                //                            spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    
    }
    
}